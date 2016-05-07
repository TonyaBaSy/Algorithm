package search;

import sorts.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tony on 2016/4/28.
 */
public class BTree {
    private final int degree = 2;
    private Node root;
    private boolean isInsert;
    private int isDelete;

    private class Node{
        private List<Node> child;
        private List<Integer> value;

        public Node(){
            child = new ArrayList<>(degree * 2 + 2);       //���ӽڵ㼯
            value = new ArrayList<>(degree * 2 + 1);    //��ǰ�ڵ��ֵ
            for(int i=0; i<degree*2+2; i++) {
                if (i != degree * 2 + 1)
                    value.add(null);
                child.add(null);
            }
            System.out.println("Child size is: " + child.size());
        }

        public int valueSize() {
            int i = 0;
            while(value.get(i) != null)
                i++;
            return i;
        }
        public int childSize() {
            int i = 0;
            while(child.get(i) != null)
                i++;
            return i;
        }
        public List<Node> getChildren() {
            return child;
        }

        public void setChild(List<Node> child) {
            if (child.size() == this.child.size())
                this.child = child;
            else if(child.size() < this.child.size()) {
                int i = 0;
                while (i<child.size())
                    this.child.set(i, child.get(i++));
            }
            else {
                int i = 0;
                while (i<this.child.size())
                    this.child.set(i, child.get(i++));
            }
        }

        public List<Integer> getValue() {
            return value;
        }

        public void setValue(List<Integer> value) {
            if (value.size() == this.value.size())
                this.value = value;
            else if(value.size() < this.value.size()) {
                int i = 0;
                while (i<value.size())
                    this.value.set(i, value.get(i++));
            }
            else {
                int i = 0;
                while (i<this.value.size())
                    this.value.set(i, value.get(i++));
            }
        }

        public boolean isPoor() {
            return !this.equals(root) && valueSize() < degree;
        }

        public boolean isReach() {
            return valueSize() > 2 * degree;
        }

        public boolean isNormal() {
            return this.equals(root) || valueSize() >= degree && valueSize() <= 2 * degree;
        }
    }

    /**
     * ���Ѳ�������һ���������ڵ����Ϊ������ƽ�񡱽ڵ�
     * @param tar ��Ҫ���ѵ�Ŀ��ڵ�
     * @return ����������ƽ�񡱽ڵ�ĸ��ڵ�
     */
    private Node split(Node tar) {
        List<Integer> values = tar.getValue();
        List<Node> children = tar.getChildren();
        int tmp = values.get(degree);
        Node father = new Node();
        father.getValue().add(tmp);

        Node left = new Node();
        left.setValue(values.subList(0,degree));
        left.setChild(children.subList(0, degree+1));

        Node right = new Node();
        right.setValue(values.subList(degree + 1, 2 * degree + 1));
        right.setChild(children.subList(degree + 1, 2 * degree + 2));

        father.getChildren().set(0, left);
        father.getChildren().set(1, right);
        return father;
    }

    /**
     * �ϲ��������ѷ��ѳ������½ڵ�ϲ������ڵ���
     * @param src ���ڵ�
     * @param tar ���ѳ����Ľڵ㣬���ҽ����������ӽڵ�
     */
    private void merge(Node src, Node tar) {
        int val = tar.getValue().get(0);
        List<Node> children = tar.getChildren();
        src.getValue().add(val);
        Collections.sort(src.getValue());
        int index = src.getValue().indexOf(val);
        src.getChildren().set(index, children.get(0));
        src.getChildren().set(index + 1, children.get(1));
    }

    private Node insert(Node root, int value) {
        if (root == null)
            return null;
        List<Integer> values = root.getValue();
        List<Node> children = root.getChildren();
        Node addition = null;
        // ���isInsertΪfalse��˵����û�н��в��룬ִ�в������
        if(!isInsert) {
            int i = 0;
            while (i < degree * 2) {
                if (values.get(i) == null || values.get(i) > value)
                    break;
                i++;
            }
            addition = insert(children.get(i), value);
            values.set(degree * 2, value);
            Collections.sort(values);
            isInsert = true;
        }
        if (addition != null) {
            merge(root, addition);
        }
        if (root.isReach()) {
            return split(root);
        }
        else
            return null;
    }

    public void insert(int value) {
        // ���Ϊ����,�����ڵ㣬������
        if (root == null) {
            root = new Node();
            root.getValue().set(0, value);
            return;
        }
        insert(root, value);
        isInsert = false;
    }

    /**
     * isDelete�Ǳ�־λ��-1��ʾ������ϣ�����û���ҵ�Ŀ�꣬ɾ��ʧ�ܣ�0��ʾ��δɾ��������������1��ʾɾ���ɹ�
     * @param root
     * @param value
     */
    public void delete(Node root, int value) {
        if (root == null) {
            isDelete = -1;
            return;
        }
        List<Integer> values = root.getValue();
        List<Node> children = root.getChildren();
        Node addition = null;
        // ���isInsertΪfalse��˵����û�н��в��룬ִ�в������
        if(isDelete == 0) {
            int i = 0;
            while (isDelete==0 && i < degree * 2) {
                if (values.get(i) == value) {
                    values.set(i, null);
                    isDelete = 1;
                }
                else if (values.get(i) == null || values.get(i) > value)
                    delete(children.get(i), value);
                i++;
            }

            values.set(degree * 2, value);
            Collections.sort(values);
            isInsert = true;
        }
    }
    public int delete(int val) {
        if (root == null) {
            return -1;
        }
        else {
            delete(root, val);
        }
        return val;
    }

    public static void main(String[] args) {
        BTree bTree = new BTree();
        int[] arrays = new int[]{1,2,3,4,5,6,7};
        bTree.insert(1);
        List<String> list = new ArrayList<>();
        for (int i=0; i<10; i++)
            list.add(null);
    }
}
