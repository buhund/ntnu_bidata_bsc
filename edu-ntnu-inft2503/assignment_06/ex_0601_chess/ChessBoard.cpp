#include "ChessBoard.h"
#include <cmath>

/**
 * Chess Board class.
 * @return
 */
std::string ChessBoard::Piece::color_string() const {
    return (color == Color::WHITE) ? "white" : "black";
}

ChessBoard::King::King(Color color) : Piece(color) {}

std::string ChessBoard::King::type() const {
    return color_string() + " King";
}

char ChessBoard::King::short_name() const {
    return (color == Color::WHITE) ? 'K' : 'k';
}

bool ChessBoard::King::valid_move(int from_x, int from_y, int to_x, int to_y) const {
    int dx = abs(to_x - from_x);
    int dy = abs(to_y - from_y);
    return (dx <= 1 && dy <= 1);
}

ChessBoard::Knight::Knight(Color color) : Piece(color) {}

std::string ChessBoard::Knight::type() const {
    return color_string() + " Knight";
}

char ChessBoard::Knight::short_name() const {
    return (color == Color::WHITE) ? 'J' : 'j';
}

bool ChessBoard::Knight::valid_move(int from_x, int from_y, int to_x, int to_y) const {
    int dx = abs(to_x - from_x);
    int dy = abs(to_y - from_y);
    return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
}

ChessBoard::ChessBoard() {
    squares.resize(8);
    for (auto &column : squares) column.resize(8);
}

bool ChessBoard::move_piece(const std::string &from, const std::string &to) {
    int from_x = from[0] - 'a';
    int from_y = stoi(std::string() + from[1]) - 1;
    int to_x = to[0] - 'a';
    int to_y = stoi(std::string() + to[1]) - 1;

    auto &piece_from = squares[from_x][from_y];
    if (piece_from) {
        if (piece_from->valid_move(from_x, from_y, to_x, to_y)) {
            auto &piece_to = squares[to_x][to_y];
            if (piece_to && piece_from->color != piece_to->color) {
                if (auto king = dynamic_cast<King *>(piece_to.get()))
                    std::cout << king->color_string() << " lost the game" << std::endl;
            }
            piece_to = std::move(piece_from);
            if (after_piece_move) after_piece_move();
            return true;
        }
    }
    return false;
}
