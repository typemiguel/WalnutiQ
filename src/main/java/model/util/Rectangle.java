package model.util;

import java.awt.geom.Point2D;

/**
 * IMPORTANT TO UNDERSTAND: the most top left corner of the plane this rectangle can be on is (0, 0)
 *
 * @author Quinn Liu (quinnliu@vt.edu)
 * @version July 12, 2014
 */
public class Rectangle {
    private Point2D topLeftCorner;
    private Point2D bottomRightCorner;

    /**
     * IMPORTANT TO UNDERSTAND: the most top left corner of the plane this rectangle can be on is (0, 0)
     */
    public Rectangle(Point2D topLeftCorner, Point2D bottomRightCorner) {
        double TLx = topLeftCorner.getX();
        double TLy = topLeftCorner.getY();
        double BRx = bottomRightCorner.getX();
        double BRy = bottomRightCorner.getY();

        if (TLx < 0 || TLy < 0 || BRx < 0 || BRy < 0) {
            throw new IllegalArgumentException("In class Rectangle constructor method the input point" +
                    " must be >= 0");
        } else if (TLx >= BRx || TLy >= BRy) {
            throw new IllegalArgumentException("In class Rectangle constructor method the parameter " +
                    "topLeftCorner is not to the top left of the parameter bottomRightCorner");
        }

        this.topLeftCorner = topLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
    }

    public double getWidth() {
        return this.bottomRightCorner.getX() - this.topLeftCorner.getX();
    }

    public double getHeight() {
        return this.bottomRightCorner.getY() - this.topLeftCorner.getY();
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
        double TLx = newTopLeftCorner.getX();
        double TLy = newTopLeftCorner.getY();
        double BRx = this.bottomRightCorner.getX();
        double BRy = this.bottomRightCorner.getY();
        if (TLx < 0 || TLy < 0) {
            throw new IllegalArgumentException("In class Rectangle isNewTopLeftCornerValid method the input point" +
                    " must be >= 0");
        } else if (TLx >= BRx || TLy >= BRy) {
            throw new IllegalArgumentException("In class Rectangle isNewTopLeftCornerValid method the input point" +
                    " is not to the top left of the current bottom right point");
        } else {
            this.topLeftCorner.setLocation(newTopLeftCorner);
        }
    }

    void setNewBottomRightCornerIfValid(Point2D newBottomRightCorner) {
        double TLx = this.topLeftCorner.getX();
        double TLy = this.topLeftCorner.getY();
        double BRx = newBottomRightCorner.getX();
        double BRy = newBottomRightCorner.getY();

        if (BRx <= 0 || BRy <= 0) {
            throw new IllegalArgumentException("In class Rectangle isNewBottomRightCornerValid method the input point" +
                    " must be >= 0");
        }
        if (TLx >= BRx || TLy >= BRy) {
            throw new IllegalArgumentException("In class Rectangle isNewBottomRightCornerValid method the input point" +
                    " is not to the bottom right of the current top left point");
        } else {
            this.bottomRightCorner.setLocation(newBottomRightCorner);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        if (bottomRightCorner != null ? !bottomRightCorner.equals(rectangle.bottomRightCorner) : rectangle.bottomRightCorner != null)
            return false;
        if (topLeftCorner != null ? !topLeftCorner.equals(rectangle.topLeftCorner) : rectangle.topLeftCorner != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = topLeftCorner != null ? topLeftCorner.hashCode() : 0;
        result = 31 * result + (bottomRightCorner != null ? bottomRightCorner.hashCode() : 0);
        return result;
    }
}
