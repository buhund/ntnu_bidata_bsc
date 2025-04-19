#ifndef CHESSBOARD_H
#define CHESSBOARD_H

#include <string>
#include <vector>
#include <functional>

/**
 * Header file for Chess Board class.
 */
class ChessBoard {
public:
    enum class Color { WHITE, BLACK };

    class Piece {
    public:
        Piece(Color color) : color(color) {}
        virtual ~Piece() {}

        Color color;
        std::string color_string() const;

        virtual std::string type() const = 0;
        virtual char short_name() const = 0;
        virtual bool valid_move(int from_x, int from_y, int to_x, int to_y) const = 0;
    };

    class King : public Piece {
    public:
        King(Color color);
        std::string type() const override;
        char short_name() const override;
        bool valid_move(int from_x, int from_y, int to_x, int to_y) const override;
    };

    class Knight : public Piece {
    public:
        Knight(Color color);
        std::string type() const override;
        char short_name() const override;
        bool valid_move(int from_x, int from_y, int to_x, int to_y) const override;
    };

    ChessBoard();

    using MoveCallback = std::function<void()>;
    MoveCallback after_piece_move;

    std::vector<std::vector<std::unique_ptr<Piece>>> squares;

    bool move_piece(const std::string &from, const std::string &to);
};

#endif
