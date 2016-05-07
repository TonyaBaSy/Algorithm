package search;

/**
 * Created by Tony on 2016/4/8.
 * 循环实现二叉排序树
 */
public class BinarySearchTreeII<Key extends Comparable<Key>, Value> {
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
        Note r = root;
        while(r!=null){
            if (r.key.compareTo(key) == 0)
                return r;
            else if(r.key.compareTo(key) > 0)
                r = r.left;
            else
                r = r.right;
        }
        return null;
    }


    public void put(Note note){
        Note res=get(note.key);
        if (res!=null) {
            res.value = note.value;
            return;
        }
        else {
            Note r = root;
            while(r!=null){
                if (r.key.compareTo(note.key) < 0)
                    r = r.right;
                else
                    r = r.left;
            }
            r = note;
        }
    }
}
