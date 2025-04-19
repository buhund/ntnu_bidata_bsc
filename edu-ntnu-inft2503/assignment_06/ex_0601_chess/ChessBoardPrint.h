#ifndef CHESSBOARDPRINT_H
#define CHESSBOARDPRINT_H

#include "ChessBoard.h"

/**
 * Header file for the supreme Chess Board Printer class.
 */
class ChessBoardPrint {
public:
    ChessBoardPrint(ChessBoard &board);
    void print_board() const;

private:
    ChessBoard &board;
};

#endif
