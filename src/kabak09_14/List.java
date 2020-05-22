package ua.khpi.oop.kabak09_14;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public class List<E> extends AbstractSequentialList<E> implements java.util.List<E>, Serializable {
    private int size;
    private List.Node<E> first;
    private List.Node<E> last;

    public List() {
        this.size = 0;
    }

    void linkLast(E e) {
        List.Node<E> l = this.last;
        List.Node<E> newNode = new List.Node(l, e, (List.Node) null);
        this.last = newNode;
        if (l == null) {
            this.first = newNode;
        } else {
            l.next = newNode;
        }

        ++this.size;
        ++this.modCount;
    }

    void linkBefore(E e, List.Node<E> succ) {
        List.Node<E> pred = succ.prev;
        List.Node<E> newNode = new List.Node(pred, e, succ);
        succ.prev = newNode;
        if (pred == null) {
            this.first = newNode;
        } else {
            pred.next = newNode;
        }

        ++this.size;
        ++this.modCount;
    }

    E unlink(List.Node<E> x) {
        E element = x.item;
        List.Node<E> next = x.next;
        List.Node<E> prev = x.prev;
        if (prev == null) {
            this.first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            this.last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        --this.size;
        ++this.modCount;
        return element;
    }

    public boolean contains(Object o) {
        return this.indexOf(o) >= 0;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        List.Node next;
        for (List.Node x = this.first; x != null; x = next) {
            next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
        }

        this.first = this.last = null;
        this.size = 0;
        ++this.modCount;
    }

    public void add(int index, E element) {
        this.checkPositionIndex(index);
        if (index == this.size) {
            this.linkLast(element);
        } else {
            this.linkBefore(element, this.node(index));
        }

    }

    public E remove(int index) {
        this.checkElementIndex(index);
        return this.unlink(this.node(index));
    }

    public Object[] toArray() {
        Object[] result = new Object[this.size];
        int i = 0;

        for (List.Node x = this.first; x != null; x = x.next) {
            result[i++] = x.item;
        }

        return result;
    }

    void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(this.size);

        for (List.Node x = this.first; x != null; x = x.next) {
            s.writeObject(x.item);
        }

    }

    public void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {

        int size = s.readInt();

        for (int i = 0; i < size; ++i) {
            this.linkLast((E) s.readObject());
        }

    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        List.Node<E> next;
        for (List.Node x = this.first; x != null; x = next) {
            result.append(x.toString());
            next = x.next;
        }
        String endResult = new String(result);
        return endResult;
    }

    //Вспомагательные:
    private boolean isElementIndex(int index) {
        return index >= 0 && index < this.size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= this.size;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + this.size;
    }

    private void checkElementIndex(int index) {
        if (!this.isElementIndex(index)) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
        }
    }

    private void checkPositionIndex(int index) {
        if (!this.isPositionIndex(index)) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
        }
    }

    public int indexOf(Object o) {
        int index = 0;
        List.Node x;
        if (o == null) {
            for (x = this.first; x != null; x = x.next) {
                if (x.item == null) {
                    return index;
                }

                ++index;
            }
        } else {
            for (x = this.first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    return index;
                }

                ++index;
            }
        }

        return -1;
    }

    public E get(int index) {
        this.checkElementIndex(index);
        return this.node(index).item;
    }

    //Итерация + Node :
    private static class Node<E> implements Serializable {
        E item;
        List.Node<E> next;
        List.Node<E> prev;

        Node(List.Node<E> prev, E element, List.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

        public String toString() {
            return item.toString();
        }
    }

    List.Node<E> node(int index) {
        List.Node x;
        int i;
        if (index < this.size >> 1) {
            x = this.first;

            for (i = 0; i < index; ++i) {
                x = x.next;
            }

            return x;
        } else {
            x = this.last;

            for (i = this.size - 1; i > index; --i) {
                x = x.prev;
            }

            return x;
        }
    }

    private class ListItr implements ListIterator<E> {
        private List.Node<E> lastReturned;
        private List.Node<E> next;
        private int nextIndex;
        private int expectedModCount;

        ListItr(int index) {
            this.expectedModCount = List.this.modCount;
            this.next = index == List.this.size ? null : List.this.node(index);
            this.nextIndex = index;
        }

        public boolean hasNext() {
            return this.nextIndex < List.this.size;
        }

        public E next() {
            this.checkForComodification();
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                this.lastReturned = this.next;
                this.next = this.next.next;
                ++this.nextIndex;
                return this.lastReturned.item;
            }
        }

        public boolean hasPrevious() {
            return this.nextIndex > 0;
        }

        public E previous() {
            this.checkForComodification();
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            } else {
                this.lastReturned = this.next = this.next == null ? List.this.last : this.next.prev;
                --this.nextIndex;
                return this.lastReturned.item;
            }
        }

        public int nextIndex() {
            return this.nextIndex;
        }

        public int previousIndex() {
            return this.nextIndex - 1;
        }

        public void remove() {
            this.checkForComodification();
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            } else {
                List.Node<E> lastNext = this.lastReturned.next;
                List.this.unlink(this.lastReturned);
                if (this.next == this.lastReturned) {
                    this.next = lastNext;
                } else {
                    --this.nextIndex;
                }

                this.lastReturned = null;
                ++this.expectedModCount;
            }
        }

        public void set(E e) {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            } else {
                this.checkForComodification();
                this.lastReturned.item = e;
            }
        }

        public void add(E e) {
            this.checkForComodification();
            this.lastReturned = null;
            if (this.next == null) {
                List.this.linkLast(e);
            } else {
                List.this.linkBefore(e, this.next);
            }

            ++this.nextIndex;
            ++this.expectedModCount;
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);

            while (List.this.modCount == this.expectedModCount && this.nextIndex < List.this.size) {
                action.accept(this.next.item);
                this.lastReturned = this.next;
                this.next = this.next.next;
                ++this.nextIndex;
            }

            this.checkForComodification();
        }

        final void checkForComodification() {
            if (List.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public ListIterator<E> listIterator(int index) {
        this.checkPositionIndex(index);
        return new List.ListItr(index);
    }

    public void sort(Comparator<? super E> c, E[] arr) {
        Object[] a = arr;
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }
}
