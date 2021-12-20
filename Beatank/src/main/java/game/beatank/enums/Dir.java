package game.beatank.enums;

/**
 *
 * @author Admin
 */
public enum Dir {
    Right(0),
    Down(1),
    Left(2),
    Up(3),
    None(4);
    
    private final int id;
    
    private Dir(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public char toChar(){
        switch (id) {
            case 0 -> {
                return 'R';
            }
            case 1 -> {
                return 'D';
            }
            case 2 -> {
                return 'L';
            }
            case 3 -> {
                return 'U';
            }
            default -> {
            }
        }
        return '#';
    }
    
    public Dir getOppositeDir(){
        switch (id) {
            case 0 -> {
                return Left;
            }
            case 1 -> {
                return Up;
            }
            case 2 -> {
                return Right;
            }
            case 3 -> {
                return Down;
            }
            default -> {
            }
        }
        return None;
    }
    
    public Dir getRightDir(){
        switch (id) {
            case 0 -> {
                return Down;
            }
            case 1 -> {
                return Left;
            }
            case 2 -> {
                return Up;
            }
            case 3 -> {
                return Right;
            }
            default -> {
            }
        }
        return None;
    }
    public Dir getLeftDir(){
        switch (id) {
            case 0 -> {
                return Up;
            }
            case 1 -> {
                return Right;
            }
            case 2 -> {
                return Down;
            }
            case 3 -> {
                return Left;
            }
            default -> {
            }
        }
        return None;
    }
}
