package hu.unideb.inf.szakdoga.model;

import hu.unideb.inf.szakdoga.exceptions.InvalidPlacingPositionException;
import hu.unideb.inf.szakdoga.exceptions.InvalidShootingPositionException;
import lombok.Data;

@Data
public class Field {

    private CellType[][] map;

    public Field() {
        map = new CellType[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                map[i][j] = CellType.WATER;
            }
        }
    }

    public boolean isAllBoatsSunken() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (map[j][i] != CellType.WATER
                        && map[j][i] != CellType.HIT
                        && map[j][i] != CellType.MISS) {
                    return false;
                }
            }
        }
        return true;
        
    }

    private boolean canPlace(int x, int y, int length, boolean vertical) {
        if (vertical) {
            if (y + length > map.length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (map[y + i][x] != CellType.WATER) {
                    return false;
                }
            }
        } else {
            if (x + length > map[0].length) {
                return false;
            }

            for (int i = 0; i < length; i++) {
                if (map[y][x + i] != CellType.WATER) {
                    return false;
                }
            }
        }
        return true;
    }

    public void place(ShipType shipType, int x, int y, boolean vertical) throws InvalidPlacingPositionException {
        int length;
        CellType cellType;
        switch (shipType) {
            case CARRIER:
                length = 5;
                cellType = CellType.CARRIER;
                break;
            case BATTLESHIP:
                length = 4;
                cellType = CellType.BATTLESHIP;
                break;
            case CRUISER:
                length = 3;
                cellType = CellType.CRUISER;
                break;
            case SUBMARINE:
                length = 3;
                cellType = CellType.SUBMARINE;
                break;
            case DESTROYER:
                length = 2;
                cellType = CellType.DESTROYER;
                break;
            default:
                length = 2;
                cellType = CellType.DESTROYER;
        }
        if (!this.canPlace(x, y, length, vertical)) {
            throw new InvalidPlacingPositionException();
        }
        if (vertical) {
            for (int i = 0; i < length; i++) {
                this.map[y + i][x] = cellType;
            }
        } else {
            for (int i = 0; i < length; i++) {
                this.map[y][x + i] = cellType;
            }
        }
    }

    private boolean canShoot(int x, int y) {
        return map[y][x] != CellType.HIT && map[y][x] != CellType.MISS;
    }

    public boolean shoot(int x, int y) throws InvalidShootingPositionException {
        if (!this.canShoot(x, y)) {
            throw new InvalidShootingPositionException();
        }
        if (map[y][x] == CellType.WATER) {
            map[y][x] = CellType.MISS;
            return false;

        } else {
            map[y][x] = CellType.HIT;
            return true;
        }
    }

}
