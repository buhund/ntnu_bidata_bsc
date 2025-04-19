#include "ChessBoardPrint.h"
#include <iostream>

/**
 * Header file for the chess board printer class.
 * @param board
 */
ChessBoardPrint::ChessBoardPrint(ChessBoard &board) : board(board) {
    board.after_piece_move = [this]() { print_board(); };
}

void ChessBoardPrint::print_board() const {
    std::cout << "  a b c d e f g h" << std::endl;
    std::cout << "  ---------------" << std::endl;
    for (int y = 7; y >= 0; --y) {
        std::cout << y + 1 << " ";
        for (int x = 0; x < 8; ++x) {
            if (board.squares[x][y]) {
                std::cout << board.squares[x][y]->short_name() << " ";
            } else {
                std::cout << ". ";
            }
        }
        std::cout << y + 1 << std::endl;
    }
    std::cout << "  ---------------" << std::endl;
    std::cout << "  a b c d e f g h" << std::endl;
}
