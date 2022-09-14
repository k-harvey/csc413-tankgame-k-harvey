package tankgame;

import java.util.Observable;

public class Collision extends Observable {

    int collisionDamage;

    public void setValue(int i){

        collisionDamage = i;
        setChanged();
        notifyObservers(this);

    }

}
