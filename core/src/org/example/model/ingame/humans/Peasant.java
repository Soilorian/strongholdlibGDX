package org.example.model.ingame.humans;


import org.example.control.enums.GameMenuMessages;
import org.example.control.menucontrollers.SoundPlayer;
import org.example.model.enums.Sounds;
import org.example.model.exceptions.CoordinatesOutOfMap;
import org.example.model.exceptions.NotInStoragesException;
import org.example.model.ingame.castle.Building;
import org.example.model.ingame.castle.Empire;
import org.example.model.ingame.humans.army.Troop;
import org.example.model.ingame.humans.army.Troops;
import org.example.model.ingame.humans.army.details.Status;
import org.example.model.ingame.map.Tile;
import org.example.model.ingame.map.resourses.Resource;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.Serializable;

public class Peasant extends Troop implements Serializable {
    private Building workplace = null;
    private Resource movingResource = null;
    private Resource inNeed = null;
    private int hp = 80;
    private Building destinationBuilding = null;
    private Status humanStatus = Status.NOTHINGNESS;

    public Peasant(Empire empire, Tile tile) {
        super(Troops.PEASANT, empire, tile);
    }


    public boolean takeFromStockpile() {
        if (getKing().takeResource(inNeed)) {
            movingResource = inNeed;
            inNeed = null;
            destinationBuilding = workplace;
            return true;
        }
        return false;
    }

    public boolean deliverGoods() throws NotInStoragesException, CoordinatesOutOfMap {
        if (getKing().addResource(movingResource)) {
            movingResource = null;
            if (inNeed != null)
                destinationBuilding = getKing().whereToGet(inNeed, getCurrentTile().getX(), getCurrentTile().getY());
            else destinationBuilding = workplace;
            return true;
        }
        return false;
    }

    public GameMenuMessages update() throws CoordinatesOutOfMap, NotInStoragesException {
        switch (humanStatus) {
            case SICK:{
                humanStatus = Status.NOTHINGNESS;
                return GameMenuMessages.HEALED;
            }
            case RECEIVING : {
                if (takeFromStockpile()) humanStatus = Status.ON_THE_WAY;
                else return GameMenuMessages.NOT_ENOUGH_RESOURCE;
                break;
            }
            case DELIVERING : {
                if (deliverGoods()) humanStatus = Status.RECEIVING;
                else return GameMenuMessages.STORAGE_FULL;
                break;
            }
            case ON_THE_WAY : {
                moveAndPatrol();
                if (destinationBuilding.atBuilding(getCurrentTile().getX(), getCurrentTile().getY())) {
                    if (destinationBuilding.equals(workplace)) {
                        workplace.setHolder(movingResource);
                        movingResource = null;
                    } else humanStatus = Status.DELIVERING;
                }
                break;
            }
        }
        return null;
    }

    public void takeDamage(int amount) {
        hp -= amount;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public Building getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Building workplace) {
        this.workplace = workplace;
    }

    public Building getDestinationBuilding() {
        return destinationBuilding;
    }

    public void setDestinationBuilding(Building destinationBuilding) {
        this.destinationBuilding = destinationBuilding;
    }


    public Status getHumanStatus() {
        return humanStatus;
    }

    public void setHumanStatus(Status humanStatus) {
        this.humanStatus = humanStatus;
    }

    public void sendToGet(Resource resource, Resource toDeliver) throws CoordinatesOutOfMap, NotInStoragesException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        setHumanStatus(Status.ON_THE_WAY);
        setDestinationBuilding(getKing().whereToGet(resource, getCurrentTile().getX(), getCurrentTile().getY()));
        if (destinationBuilding == null) {
            SoundPlayer.play(Sounds.AKHEY); //TODO replace with proper sound
            setHumanStatus(Status.AT_WORK);
        } else {
            inNeed = resource;
            movingResource = toDeliver;
        }
    }
}
