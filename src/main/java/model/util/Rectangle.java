package model.util;

import java.awt.geom.Point2D;

/**
 * TODO: unit test this class
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 12, 2014
 */
public class Rectangle {
    private Point2D topLeftCorner;
    private Point2D bottomRightCorner;

    public Rectangle(Point2D topLeftCorner, Point2D bottomRightCorner) {
        int TLx = (int) topLeftCorner.getX();
        int TLy = (int) topLeftCorner.getY();
        int BRx = (int) bottomRightCorner.getX();
        int BRy = (int) bottomRightCorner.getY();

        this.setNewTopLeftCornerIfValid(topLeftCorner);
        this.setNewBottomRightCornerIfValid(bottomRightCorner);
    }

    public Point2D getTopLeftCorner() {
        return this.topLeftCorner;
    }

    public void setTopLeftCorner(Point2D topLeftCorner) {
        this.setNewTopLeftCornerIfValid(topLeftCorner);
    }

    public Point2D getBottomRightCorner() {
        return this.bottomRightCorner;
    }

    public void setBottomRightCorner(Point2D bottomRightCorner) {
        this.setNewBottomRightCornerIfValid(bottomRightCorner);
    }

    void setNewTopLeftCornerIfValid(Point2D newTopLeftCorner) {
        int TLx = (int) newTopLeftCorner.getX();
        int TLy = (int) newTopLeftCorner.getY();
        int BRx = (int) this.bottomRightCorner.getX();
        int BRy = (int) this.bottomRightCorner.getY();
        if (TLx < 0 || TLy < 0) {
            throw new IllegalArgumentException("In class Rectangle isNewTopLeftCornerValid method the input point" +
                    " must be >= 0");
        } else if (TLx >= BRx || TLy >= BRy) {
            throw new IllegalArgumentException("In class Rectangle isNewTopLeftCornerValid method the input point" +
                    " is not to the top left of the current bottom right point");
        }
        this.topLeftCorner = newTopLeftCorner;
    }

    void setNewBottomRightCornerIfValid(Point2D newBottomRightCorner) {
        int TLx = (int) this.topLeftCorner.getX();
        int TLy = (int) this.topLeftCorner.getY();
        int BRx = (int) newBottomRightCorner.getX();
        int BRy = (int) newBottomRightCorner.getY();

        if (BRx <= 0 || BRy <= 0) {
            throw new IllegalArgumentException("In class Rectangle isNewBottomRightCornerValid method the input point" +
                    " must be >= 0");
        }
        if (TLx >= BRx || TLy >= BRy) {
            throw new IllegalArgumentException("In class Rectangle isNewBottomRightCornerValid method the input point" +
                    " is not to the bottom right of the current top left point");
        }
        this.bottomRightCorner = newBottomRightCorner;
    }
}
