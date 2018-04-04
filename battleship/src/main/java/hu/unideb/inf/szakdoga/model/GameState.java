package hu.unideb.inf.szakdoga.model;


public enum GameState {

    PLAYERS_PLACING("Players Placing"),
    PLAYER_ONE_SHOOTING("Player One Shooting"),
    PLAYER_TWO_SHOOTING("Player Two Shooting"),
    PLAYER_ONE_WON("Player One Won"),
    PLAYER_TWO_WON("Player Two Won");
    private final String text;
    GameState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
