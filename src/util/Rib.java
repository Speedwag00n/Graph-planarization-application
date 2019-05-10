package util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class Rib {

    private int i;
    private int j;

    private static WeakHashMap<Rib, WeakReference<Rib>> ids = new WeakHashMap<>();

    private Rib(int i, int j){
        this.i = i;
        this.j = j;
    }

    public static Rib getRib(int i, int j){

        Rib rib = new Rib(i, j);
        if (!ids.containsKey(rib)) {
            ids.put(rib, new WeakReference<>(rib));
            return rib;
        }

        return ids.get(rib).get();

    }

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rib object = (Rib) obj;
        if (this.i != object.i)
            return false;
        if (this.j != object.j)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(" + i + ";" + j + ")";
    }

    @Override
    public int hashCode(){
        final int prime = 17;
        int result = 1;
        result = result*prime + i;
        result = result*prime + j;
        return result;
    }

}
