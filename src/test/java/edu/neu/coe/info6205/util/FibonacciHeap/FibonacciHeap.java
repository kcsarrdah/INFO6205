package edu.neu.coe.info6205.util.FibonacciHeap;

public class FibonacciHeap<T> {
    private int n;
    private FibonacciHeapNode<T> min;

    public boolean isEmpty() {
        return min == null;
    }

    public void clear() {
        n = 0;
        min = null;
    }

    public FibonacciHeapNode<T> insert(T key, double priority) {
        if(priority < 0) throw new IllegalArgumentException();
        if(key == null) throw new NullPointerException();
        FibonacciHeapNode<T> node = new FibonacciHeapNode<T>(key, priority);
        if (min == null) {
            min = node;
        } else {
            min.insert(node);
            if (node.compareTo(min) < 0) {
                min = node;
            }
        }
        n++;
        return node;
    }

    public FibonacciHeapNode<T> min() {
        return min;
    }

    public FibonacciHeapNode<T> removeMin() {
        FibonacciHeapNode<T> z = min;
        if (z != null) {
            int numKids = z.degree();
            FibonacciHeapNode<T> x = z.child;
            FibonacciHeapNode<T> tempRight;
            while (numKids > 0) {
                tempRight = x.right;
                x.remove();
                min.insert(x);
                x.parent = null;
                x = tempRight;
                numKids--;
            }
            z.remove();
            if (z == z.right) {
                min = null;
            } else {
                min = z.right;
                consolidate();
            }
            n--;
        }
        return z;
    }

    public void decreaseKey(FibonacciHeapNode<T> x, double priority) {

        if(priority < 0) throw new IllegalArgumentException();

        if (priority > x.priority) {
            throw new IllegalArgumentException("New priority is greater than current priority.");
        }
        x.priority = priority;
        FibonacciHeapNode<T> y = x.parent;
        if (y != null && x.compareTo(y) < 0) {
            cut(x, y);
            cascadingCut(y);
        }
        if (x.compareTo(min) < 0) {
            min = x;
        }
    }

//    public void delete(FibonacciHeapNode<T> x) {
//        if(getRootParent(x) != min) throw new NullPointerException();
//
//        x.remove();
//
//        decreaseKey(x, Double.NEGATIVE_INFINITY);
//        removeMin();
//    }

    private void cut(FibonacciHeapNode<T> x, FibonacciHeapNode<T> y) {
        y.removeChild(x);
        min.insert(x);
        x.parent = null;
        x.mark = false;
    }

    private void cascadingCut(FibonacciHeapNode<T> y) {
        FibonacciHeapNode<T> z = y.parent;
        if (z != null) {
            if (!y.mark) {
                y.mark = true;
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(n) / Math.log(2));
        FibonacciHeapNode<T>[] A = new FibonacciHeapNode[maxDegree + 1];
        for (int i = 0; i <= maxDegree; i++) {
            A[i] = null;
        }
        int numRoots = 0;
        FibonacciHeapNode<T> x = min;
        if (x != null) {
            numRoots++;
            x = x.right;
            while (x != min) {
                numRoots++;
                x = x.right;
            }
        }
        while (numRoots > 0) {
            int d = x.degree();
            FibonacciHeapNode<T> next = x.right;
            while (A[d] != null) {
                FibonacciHeapNode<T> y = A[d];
                if (x.compareTo(y) > 0) {
                    FibonacciHeapNode<T> temp = y;
                    y = x;
                    x = temp;
                }
                link(y, x);
                A[d] = null;
                d++;
            }
            A[d] = x;
            x = next;
            numRoots--;
        }
        min = null;
        for (int i = 0; i <= maxDegree; i++) {
            if (A[i] != null) {
                if (min == null) {
                    min = A[i];
                } else {
                    min.insert(A[i]);
                    if (A[i].compareTo(min) < 0) {
                        min = A[i];
                    }
                }
            }
        }
    }
    public FibonacciHeapNode<T> peekMin() {
        return min;
    }

    public int size() {
        return n;
    }

    public FibonacciHeapNode<T> deleteMin() {

        FibonacciHeapNode<T> minNode = min;
        if (minNode != null) {
            int numKids = minNode.degree();
            FibonacciHeapNode<T> x = minNode.child;
            FibonacciHeapNode<T> tempRight;
            while (numKids > 0) {
                tempRight = x.right;
                x.remove();
                min.insert(x);
                x.parent = null;
                x = tempRight;
                numKids--;
            }
            minNode.remove();
            if (minNode == minNode.right) {
                min = null;
            } else {
                min = minNode.right;
                consolidate();
            }
            n--;
        }
        return minNode;
    }
    private void link(FibonacciHeapNode<T> y, FibonacciHeapNode<T> x) {
        y.remove();
        x.addChild(y);
        y.mark = false;
    }
    private FibonacciHeapNode<T> getRootParent(FibonacciHeapNode<T> x){
        if(x.parent == null) return x;
        return getRootParent(x.parent);
    }
}
