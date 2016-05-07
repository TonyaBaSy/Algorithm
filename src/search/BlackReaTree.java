package search;

/**
 * �������ʵ���ѵ�
 *  1.����ĵݹ����
 *  2.�����е�֧�ܽṹ�е�һ��ССϸ�ڣ�ȴ�Ǿ����˳ɰܹؼ�
 *  3.ʹ�÷���������ֱ�ӵ��ã�������Ϊ�˰�ȫ����Ϊ������ԣ�
 *    n.right����nΪnullʱ��������Ǵ���ģ����n.right()
 *    �������ڷ����н��С��ǿա����жϣ��ͷǳ����ɵĽ��������
 *    �����⡣����һ��ֱ��ȫƪ��������ã�һ����������Ҫ���
 *    �����޸ġ�
 * Created by Tony on 2016/4/12.
 */
public class BlackReaTree<Key extends Comparable<Key>, Value> {
    public static final boolean RED = true;
    public static final boolean BLACK = false;
//    public Node test;
    private Node root;
    public int rank = -1;

    private boolean judge(Node root, int i) {
        if (!isRed(root))
            i++;

        if (root == null) {
            if (rank == -1)
                rank = i;
            return rank == i;
        }

        boolean b = false;

        b = judge(root.left, i);
        b = judge(root.right, i);

        return b;
    }

    public boolean judge() {
        return judge(root, 0);
    }

    public class Node{
        Key key;
        Value value;
        Node left, right, father;
        boolean isRed = true;
        int n;

        Node(Node f, Key k, Value v, boolean isRed, int n) {
            this.father = f;
            this.key = k;
            this.value = v;
            this.isRed = isRed;
            this.n = n;
        }


        @Override
        public Node clone() {
            Node copy = new Node(father, key, value, isRed, n);
            copy.left = left;
            copy.right = right;
            return copy;
        }

        public boolean equals(Node o){
            if (o == this)
                return true;
            if (o == null)
                return false;
            Node n = (Node) o;

            return (n.father == this.father
                    && n.left == this.left
                    && n.right == this.right
                    && n.key == this.key
                    && n.value == this.value
                    && n.n == this.n
                    && n.isRed == this.isRed);
        }

    }

    public int getN(Node n) {
        if (n == null)
            return 0;
        else
            return n.n;
    }

    public boolean isRed(Node n){
        if (n != null)
            return n.isRed;
        return false;
    }

    public Node getRight(Node n) {
        if (n == null)
            return null;
        return n.right;
    }
    
    public Node getLeft(Node n) {
        if (n == null)
            return null;
        return n.left;
    }
    
    private Node rotateLeft(Node a){
        Node b = a.right;
        if (a.father != null) {
            if (a.equals(a.father.left))
                a.father.left = b;
            else
                a.father.right = b;
        }
        a.right = b.left;
        if (b.left != null)
            b.left.father = a;
        b.left = a;
        b.isRed = a.isRed;
        a.isRed = RED;
        b.father = a.father;
        a.father = b;
        return b;
    }

    private Node rotateRight(Node a){
        Node b = a.left;
        if (a.father != null) {
            if (a.equals(a.father.left))
                a.father.left = b;
            else
                a.father.right = b;
        }
        a.left = b.right;
        if (b.right != null)
            b.right.father = a;
        b.right = a;
        b.isRed = a.isRed;
        a.isRed = RED;
        b.father = a.father;
        a.father = b;
        return b;
    }

    private Node get(Node root, Key key){
        if (root == null)
            return null;
        else if (root.key.compareTo(key) == 0)
            return root;
        else if (root.key.compareTo(key) > 0)
            return get(root.left, key);
        else
            return get(root.right, key);
    }

    public Node get(Key key) {
        return get(root, key);
    }

    private Node put(Node root, Key key, Value value) {
        if (root == null)
            return new Node(null, key, value, RED, 1);
        else if (root.key.compareTo(key) < 0) {
            root.right = put(root.right, key, value);
            // �Ի�õĽڵ��father����и�ֵ
            root.right.father = root;
            // ��õ�ǰ�ڵ�
            root = root.right;
        }
        else if (root.key.compareTo(key) > 0) {
            root.left = put(root.left, key, value);
            root.left.father = root;
            root = root.left;
        }
        else {
            root.value = value;
            return root;
        }

        // �����ڡ�֧�ܽṹ������
        if (root.father.father != null
                && isRed(root)
                && isRed(root.father.father.left)
                && isRed(root.father.father.right)) {
            root.father.father.left.isRed = BLACK;
            root.father.father.right.isRed = BLACK;
            root.father.father.isRed = RED;
            return root.father;
        }
        root = root.father;
        // ֮����
        if (isRed(root.left) && isRed(root.left.right))
            root.left = rotateLeft(root.left);
        else if (isRed(root.right) && isRed(root.right.left))
            root.right = rotateRight(root.right);
        // һ����
        if (isRed(root.right) && isRed(root.right.right))
            root = rotateLeft(root);
        else if (isRed(root.left) && isRed(root.left.left))
            root = rotateRight(root);

        root.n = getN(root.left) + getN(root.right) + 1;
        return root;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
        this.root.isRed = BLACK;
    }

    /**
     * nΪ�������α�����������������delete����
     * �������۵ľ�Ϊn��Ϊ��������n�ĺ���������һ����Ϊ��ɫ��
     * ����������ɾ��n��ֻ�����������˫�ڡ�����������ɾ����������delete����
     * @param n ��ɾ�����߱������Ľڵ�n
     * @return
     */
    private void fix(Node n){
        Node p = n.father;
        Node s;
        boolean isLeft = true;
        if (n.equals(this.root))
            return;
        // ��ɾ���Ľڵ�n�����
        if (n.father.left.equals(n)) {
            s = p.right;
        }
        // ��ɾ���Ľڵ�n���ұ�
        else {
            s = p.left;
            isLeft = false;
        }
        Node sl = getLeft(s);
        Node sr = getRight(s);

        // ȫ��
        if (!isRed(p) && !isRed(s) && !isRed(sr) && !isRed(sl)) {
            // ������Բ������ s==null �����������������Υ��������5
            s.isRed = RED;
            fix(p);
        }
        // sΪ��
        else if (isRed(s)) {
            if (isLeft) {
                rotateLeft(p);
                p = n.father;
                s = p.right;
            }
            else {
                rotateRight(p);
                p = n.father;
                s = p.left;
            }
            sl = getLeft(s);
            sr = getRight(s);
        }
        // p�죬ֶ��ȫ��
        if (isRed(p) && !isRed(sl) && !isRed(sr)) {
            p.isRed = BLACK;
            if (s !=null)
                s.isRed = RED;
            return;
        }
        // ֶ����һ��Ϊ��
        if (isLeft) {
            if (isRed(sl)) {
                rotateRight(s);
                s = p.right;
                sl = getLeft(s);
                sr = getRight(s);
            }
            if (isRed(sr)) {
                rotateLeft(p);
                sr.isRed = BLACK;
                p.isRed = BLACK;
            }
        }
        else {
            if (isRed(sr)) {
                rotateLeft(s);
                s = p.left;
                sl = getLeft(s);
                sr = getRight(s);
            }
            if (isRed(sl)) {
                rotateRight(p);
                sl.isRed = BLACK;
                p.isRed = BLACK;
            }
        }
    }

    /**
     * �ҵ���������С��
     * @param next �������Һ���
     * @return
     */
    private Node substitute(Node next) {
        if (next.left == null)
            return next;
        else
            return substitute(next.left);
    }

    private Node doDelete(Node root) {
        Node res = null;
        // ��ɾ���Ľڵ�nΪ���ڵ㣬ֱ��ɾ��
        if (root.equals(this.root)) {
            res = root.clone();
            this.root = null;
        }
        // nΪ��ɫ�ڵ㣬��n������һ�����ӣ�ֱ��ɾ��
        else if (isRed(root) && (root.left == null || root.right == null)) {
            Node child;
            if (root.left == null)
                child = root.right;
            else
                child = root.left;
            if (root.father.left.equals(root))
                root.father.left = child;
            else
                root.father.right = child;
            res = root.clone();
        }
        // nΪ��ɫ����n������һ������
        else if (!isRed(root) && (root.left==null || root.right==null)) {
            Node child;
            if (root.left==null)
                child = root.right;
            else
                child = root.left;
            // childΪ��ɫ��ֱ��ɾ�����ı亢����ɫ
            if (isRed(child)){
                if (root.equals(root.father.left))
                    root.father.left = child;
                else
                    root.father.right = child;
                child.father = root.father;
                res = root.clone();
                child.isRed = BLACK;
            }
            // childΪ��ɫ���޸���ɾ��
            else {
                fix(root);
                if (root.equals(root.father.left))
                    root.father.left = child;
                else
                    root.father.right = child;
                res = root.clone();
            }
        }
        return res;
    }

    private Node delete(Node root, Key key){
        Node res = null;
        // ���û�б�������ɾ�ڵ㣬����null
        if (root == null)
            return null;
        if (root.key.compareTo(key) < 0)
            return delete(root.right, key);
        else if (root.key.compareTo(key) > 0)
            return delete(root.left, key);
        else {
            if (root.left != null && root.right != null) {
                Node subs = substitute(root.right);
                root.value = subs.value;
                root.key = subs.key;
                root = subs;
            }
            res = doDelete(root);
        }
        return res;
    }

    public Node delete(Key key) {
        return delete(root, key);
    }

    public static void main(String[] args) {
        int[] src = {12,1,9,2,0,11,7,19,4,15,18,5,14,13,10,16,6,3,8,17};
//        final int N = 15;
        BlackReaTree<Integer, Integer> brTree = new BlackReaTree<>();
        Integer[] keys = new Integer[src.length];
        Integer[] values = new Integer[src.length];



//        brTree.test = brTree.new Node(null, 13, 0, false, 0);

        for (int i=0; i < src.length; i++) {
            keys[i] = src[i];
            values[i] = 0;
//            System.out.printf("(%2d, %2d)\n", keys[i], values[i]);
            brTree.put(keys[i], values[i]);
        }

        for (int i=0; i < src.length; i++) {
            keys[i] = src[i];
            values[i] = 0;
            brTree.delete(keys[i]);
//            System.out.printf("(%2d, %2d)\n", keys[i], values[i]);
        }
    }
}
