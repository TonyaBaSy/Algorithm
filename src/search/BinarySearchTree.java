package search;

/**
 * Created by Tony on 2016/4/8.
 * 递归实现二叉排序树
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private Note root;
    private class Note{
        private Note left, right;
        private Key key;
        private Value value;
        int n; //子树节点数目
        Note(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            this.n = n;
        }

    }

    public int size(){
        return root.n;
    }
    public int size(Note note){
        if (note == null)
            return -1;
        else
            return note.n;
    }

    public Note get(Key key) {
        return get(key, root);
    }

    private Note get(Key key, Note root){
        if (root == null)
            return null;
        if (key.compareTo(root.key) == 0)
            return root;
        else if(key.compareTo(root.key) < 0)
            return get(key, root.left);
        else
            return get(key, root.right);
    }

    public void put(Note note){
        Note res=get(note.key);
        if (res!=null) {
            res.value = note.value;
            return;
        }
        else {
            put(note, root);
        }
    }
    private void put(Note note, Note root){
        if (root == null) {
            root = note;
            return;
        }
        if (note.key.compareTo(root.key) < 0)
            put(note, root.left);
        else
            put(note, root.right);
    }
}
