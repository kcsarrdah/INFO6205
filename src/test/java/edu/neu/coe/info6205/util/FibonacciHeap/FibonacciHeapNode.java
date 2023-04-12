package edu.neu.coe.info6205.util.FibonacciHeap;

public class FibonacciHeapNode<T> implements Comparable<FibonacciHeapNode<T>> {
    public T key;
    public double priority;
    public FibonacciHeapNode<T> parent;
    public FibonacciHeapNode<T> child;
    public FibonacciHeapNode<T> left;
    public FibonacciHeapNode<T> right;
    public int degree;
    public boolean mark;

    public FibonacciHeapNode(T key, double priority) {
        this.key = key;
        this.priority = priority;
        this.parent = null;
        this.child = null;
        this.left = this;
        this.right = this;
        this.degree = 0;
        this.mark = false;
    }

    public void insert(FibonacciHeapNode<T> node) {
        node.left = this;
        node.right = this.right;
        this.right.left = node;
        this.right = node;
    }

    public void remove() {
        this.left.right = this.right;
        this.right.left = this.left;
    }

    public void addChild(FibonacciHeapNode<T> child) {
        if (this.child == null) {
            this.child = child;
        } else {
            this.child.insert(child);
        }
        child.parent = this;
        this.degree++;
    }

    public void removeChild(FibonacciHeapNode<T> child) {
        if (this.child == child) {
            this.child = child.right;
        }
        child.remove();
        this.degree--;
    }

    public int compareTo(FibonacciHeapNode<T> other) {

        return Double.compare(this.priority, other.priority);
    }

    public int degree() {
        return degree;
    }
}

