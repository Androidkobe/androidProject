package com.example.demo.sundu.developer.sensorxyz;

public class CompressHelper {
    private static float MIN_FLT_TO_AVOID_SINGULARITY = 0.0001f;

    /**
     * Convert quaternion to rotation matrix
     * <p>
     * Quaternion
     * Q = |X Y Z W|
     * <p>
     * Rotation Matrix
     * /  R[ 0]   R[ 1]   R[ 2]   0  \
     * |  R[ 4]   R[ 5]   R[ 6]   0  |
     * |  R[ 8]   R[ 9]   R[10]   0  |
     * \  0       0       0       1  /
     * <p>
     * M = 1- 2(Y*Y + Z*Z)  2XY - 2ZW       2XZ + 2YW       0
     * 2XY + 2ZW        1 - 2(XX + ZZ)  2YZ - 2XW       0
     * 2XZ - 2YW        2YZ + 2XW       1 - 2(XX + ZZ)  0
     * 0                0               0               1
     */
    public static void quatToRotMat(float rotationMat[], float quat[]) {
        float X = quat[0];
        float Y = quat[1];
        float Z = quat[2];
        float W = quat[3];

        float xx = X * X;
        float xy = X * Y;
        float xz = X * Z;
        float xw = X * W;
        float yy = Y * Y;
        float yz = Y * Z;
        float yw = Y * W;
        float zz = Z * Z;
        float zw = Z * W;

        rotationMat[0] = 1 - 2 * (yy + zz);
        rotationMat[1] = 2 * (xy - zw);
        rotationMat[2] = 2 * (xz + yw);
        rotationMat[3] = 2 * (xy + zw);
        rotationMat[4] = 1 - 2 * (xx + zz);
        rotationMat[5] = 2 * (yz - xw);
        rotationMat[6] = 2 * (xz - yw);
        rotationMat[7] = 2 * (yz + xw);
        rotationMat[8] = 1 - 2 * (xx + yy);
    }

    /**
     * Convert rotation matrix to Orientation Sensor as defined in Sensor.TYPE_ORIENTATION:
     * <p>
     * values[0]: Azimuth, angle between the magnetic north direction and the y-axis,
     * around the z-axis (0 to 359). 0=North, 90=East, 180=South, 270=West
     * <p>
     * values[1]: Pitch, rotation around x-axis (-180 to 180),
     * with positive values when the z-axis moves toward the y-axis.
     * <p>
     * values[2]: Roll, rotation around y-axis (-90 to 90),
     * with positive values when the x-axis moves toward the z-axis.
     */
    public static void rotMatToOrient(float values[], float rotationMat[]) {
        float xunit[] = {
                rotationMat[0], rotationMat[3], rotationMat[6]
        };
        float yunit[] = {
                rotationMat[1], rotationMat[4], rotationMat[7]
        };
        float zunit[] = {
                rotationMat[2], rotationMat[5], rotationMat[8]
        };
        float xnorm = (float) Math.sqrt(xunit[0] * xunit[0] + xunit[1] * xunit[1]);

        if (Math.abs(zunit[2]) < MIN_FLT_TO_AVOID_SINGULARITY) {
            zunit[2] = MIN_FLT_TO_AVOID_SINGULARITY * (zunit[2] < 0 ? -1 : 1);
        }

        if (Math.abs(xunit[0]) < MIN_FLT_TO_AVOID_SINGULARITY) {
            xunit[0] = MIN_FLT_TO_AVOID_SINGULARITY * (xunit[0] < 0 ? -1 : 1);
        }

        if (Math.abs(xnorm) < MIN_FLT_TO_AVOID_SINGULARITY) {
            xnorm = MIN_FLT_TO_AVOID_SINGULARITY * (xnorm < 0 ? -1 : 1);
        }

        final float rad2deg = (float) (180.0f / Math.PI);
        values[0] = (float) (rad2deg * Math.atan2(xunit[1], xunit[0]));
        values[0] = 360.0f - values[0] % 360.0f;
        values[1] = (float) (-rad2deg * Math.atan2(yunit[2], zunit[2]));
        values[2] = (float) (rad2deg * Math.atan2(xunit[2], xnorm));
    }
}
