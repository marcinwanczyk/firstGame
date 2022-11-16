public class Wave {
    private int number; // numer fali
    private boolean stateOfWave; // status czy sie zakonczyla czy nie
    Enemy[] enemyBoard;

    //konstruktor
    public Wave(int number, Object[][] board) {
        this.number = number;
        setStateOfWave(true);
        enemyBoard = new Enemy[number];
        for (int i = 0; i < number; i++)
            enemyBoard[i] = new Enemy(100, 0, 100, board);
    }

    public void isAlive(Object[][] board) {
        int check = 0;
        for (int i = 0; i < number; i++) {
            if (enemyBoard[i] != null)
                check++;
        }
        if (check == 0)
            setStateOfWave(false);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isStateOfWave() {
        return stateOfWave;
    }

    public void setStateOfWave(boolean stateOfWave) {
        this.stateOfWave = stateOfWave;
    }

    public Enemy[] getEnemyBoard() {
        return enemyBoard;
    }
}