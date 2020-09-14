package com.gpj.example.thread.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/1 10:21 上午
 */
public class TypeParameterTest {
    public static <T extends Comparable<T>> void mySort1(List<T> list) {
        Collections.sort(list);
    }

    public static <T extends Comparable<? super T>> void mySort2(List<T> list) {
        Collections.sort(list);
    }

    public static void main(String[] args){
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(25));
        animals.add(new Dog(35));
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog(5));
        dogs.add(new Dog(18));
        //mySort1(animals);
        //mySort1(dogs);
        mySort2(animals);
        mySort2(dogs);
    }
}

class Animal implements Comparable<Animal> {
    protected int age;
    public Animal(int age){
        this.age=age;
    }
    @Override
    public int compareTo(Animal other) {
        return this.age - other.age;
    }
}
class Dog extends Animal {
    public Dog(int age) {
        super(age);
    }
}