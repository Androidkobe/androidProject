package com.example.javalearn;

import java.util.ArrayList;
import java.util.List;

public class Test {


    // 主函数
    //上边界
    //下边界
    public static void main(String[] strs) {
        shangbianjie();
        xiabianjie();
    }


    // 吃水果
    public static void eatFruit(Container<? extends Fruit> container) {
        // 取出水果
        Fruit obj = container.getObj();
        // 打印
        System.out.println("我正在吃" + obj.getName());
    }

    // 吃肉
    public static void eatMeat(Container<? extends Meat> container) {
        // 取出肉
        Meat obj = container.getObj();
        // 打印
        System.out.println("我正在吃" + obj.getName());
    }


    // 加菜
    public static void addDish(Container<? super Meat> container) {
        // 装土猪肉
        container.setObj(new Pork());
        // 装牛肉
        container.setObj(new Pork());
    }

    // 主函数
    public static void xiabianjie() {
        // 菜盘
        Container<Food> foods = new Container<Food>();
        // 专用装肉盘
        Container<Meat> meats = new Container<Meat>();
        // 水果篮
        Container<Fruit> fruits = new Container<Fruit>();

        // 我们吃饭的时候菜吃完，所以我们加菜
        // 厨师准备用盘子装菜

        // 用菜盘装菜
        addDish(foods);
        // 用专用装肉盘装菜
        addDish(meats);
        // 但不能用水果篮装菜，一使用编译器就会提示我们异常
//    addDish(fruits);
    }


    public static void shangbianjie(){
        // 水果盘
        Container<Fruit> fruits = new Container<Fruit>();
        // 香蕉篮
        Container<Banana> bananas = new Container<Banana>();
        // 菜盘
        Container<Pork> porks = new Container<Pork>();

        // 一个水果
        Fruit fruit = new Fruit();
        // 一根香蕉
        Banana banana = new Banana();
        // 一个苹果
        Apple apple = new Apple();

        // 一块猪肉
        Pork pork = new Pork();

        // 把洗好的水果装盘
        fruits.setObj(fruit);
        fruits.setObj(apple);
        bananas.setObj(banana);
        // 把炒好的土猪肉装盘
        porks.setObj(pork);

        // 调用一下吃水果的方法进行吃水果
        eatFruit(fruits);
        // 调用一下吃水果的方法进行吃香蕉
        eatFruit(bananas);

        // 调用一下吃水果的方法进行吃猪肉，这个时候编译器就会提示异常
        // 因为吃水果的方法进行了上限的设置
//    eatFruit(porks);
        // 所以吃肉的就得使用吃肉的方法
        eatMeat(porks);
    }

    // 食物
    static class Food {
        public String getName(){
            return getClass().getName();
        }
    }

    // 肉
    static class Meat extends Food {
        public String getName(){
            return getClass().getName();
        }
    }

    // 猪肉
    static class Pork extends Meat {
        public String getName(){
            return getClass().getName();
        }
    }

    // 牛肉
    static class Beef extends Meat {
        public String getName(){
            return getClass().getName();
        }
    }

    // 水果
    static class Fruit extends Food {
        public String getName(){
            return getClass().getName();
        }
    }

    // 苹果
    static class Apple extends Fruit {
        public String getName(){
            return getClass().getName();
        }
    }

    // 香蕉
    static class Banana extends Fruit {
        public String getName(){
            return getClass().getName();
        }
    }

    // 青苹果
    static class GreenApple extends Apple {
        public String getName(){
            return getClass().getName();
        }
    }

    // 红苹果
    static class RedApple extends Apple {
        public String getName(){
            return getClass().getName();
        }
    }

    // 容器类（装食物用）
    static class Container<T> {
        private T obj;

        public Container() {
        }

        public Container(T obj) {
            this.obj = obj;
        }

        public T getObj() {
            return obj;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }
    }
}
