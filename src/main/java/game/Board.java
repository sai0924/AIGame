package game;

import boards.TicTacToeBoard;

public interface Board {

    Board move(Move move);

    Board getCopy();
}
