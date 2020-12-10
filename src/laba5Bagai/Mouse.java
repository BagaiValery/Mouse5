package laba5Bagai;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.peer.MouseInfoPeer;

public class Mouse extends GraficDisplay implements MouseListener, MouseMotionListener  {
	
	private double comparePoint(Point p1, Point p2) {
		 return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
     }
	
	private CardinationPoint find(int x, int y) {
		CardinationPoint smp = new CardinationPoint();
		CardinationPoint smp2 = new CardinationPoint();
        double r, r2 = 1000;
        for (int i = 0; i < graphicsData.length; i++) {
            Point p = new Point();
            p.x = x;
            p.y = y;
            Point p2 = new Point();
            p2.x = graphicsDataI[i][0];
            p2.y = graphicsDataI[i][1];
            r = comparePoint(p, p2);
            if (r < 7.0) {
                smp.x = graphicsDataI[i][0];
                smp.y = graphicsDataI[i][1];
                smp.xd = graphicsData[i][0];
                smp.yd = graphicsData[i][1];
                smp.n = i;
                if (r < r2) {
                    smp2 = smp;
                }
                return smp2;
            }
        }
        return null;
    }
	
	public void mouseDragged(MouseEvent arg0) {
		if (selMode) {
            if (!transform)
                rect.setFrame(mausePX, mausePY, e.getX() - rect.getX(),
                        e.getY() - rect.getY());
            else
                rect.setFrame(-mausePY + getHeight(), mausePX, -arg0.getY()
                        + mausePY, e.getX() - mausePX);
            repaint();
		}
		 if (dragMode) {
             if (!transform) {
                 if (pointToXY(e.getX(), e.getY()).y < maxY && pointToXY(e.getX(), e.getY()).y > minY) {
                     graphicsData[SMP.n][1] = pointToXY(e.getX(), e.getY()).y;
                     SMP.yd = pointToXY(e.getX(), e.getY()).y;
                     SMP.y = e.getY();
                 }
             } else {
                 if (pointToXY(e.getX(), e.getY()).y < maxY && pointToXY(e.getX(), e.getY()).y > minY) {
                     graphicsData[SMP.n][1] = pointToXY(e.getX(), e.getY()).y;
                     SMP.yd = pointToXY(e.getX(), e.getY()).y;
                     SMP.x = e.getX();
                 }
             }
             repaint();
         }

	}

	
	public void mouseMoved(MouseEvent arg0) {
		CardinationPoint smp;
        smp = find(arg0.getX(), arg0.getY());
        if (smp != null)
            SMP = smp;
        else SMP = null;
        repaint();

	}

	
	public void mouseClicked(MouseEvent arg0) {
		f (arg0.getButton() != 3)
        return;

    try
    {
        zone = stack.pop();
    }
    catch (EmptyStackException err)
    {
    }

    if (stack.empty())
        zoom = false;
    repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		selMode = true;
        mausePX = arg0.getX();
        mausePY = arg0.getY();
        rect.setFrame(arg0.getX(), arg0.getY(), 0, 0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		rect.setFrame(0, 0, 0, 0);
        if (arg0.getButton() != 1)
        {
            repaint();
            return;
        }
        if (selMode)
        {
            if (!transform)
            {
                if (arg0.getX() <= mausePX || arg0.getY() <= mausePY)
                    return;
                int eY = arg0.getY();
                int eX = arg0.getX();
                if (eY > getHeight())
                    eY = getHeight();
                if (eX > getWidth())
                    eX = getWidth();
                double MAXX = pointToXY(eX, 0).x;
                double MINX = pointToXY(mausePX, 0).x;
                double MAXY = pointToXY(0, mausePY).y;
                double MINY = pointToXY(0, eY).y;
                stack.push(zone);
                zone = new Zone();
                zone.use = true;
                zone.MAXX = MAXX;
                zone.MINX = MINX;
                zone.MINY = MINY;
                zone.MAXY = MAXY;
                selMode = false;
                zoom = true;
            } else
                {
                if (pointToXY(mausePX, 0).y <= pointToXY(arg0.getX(), 0).y
                        || pointToXY(0, arg0.getY()).x <= pointToXY(0, mausePY).x)
                    return;
                int eY = arg0.getY();
                int eX = arg0.getX();
                if (eY < 0)
                    eY = 0;
                if (eX > getWidth())
                    eX = getWidth();
                stack.push(zone);
                zone = new Zone();
                zone.use = true;
                zone.MAXY = pointToXY(mausePX, 0).y;
                zone.MAXX = pointToXY(0, eY).x;
                zone.MINX = pointToXY(0, mausePY).x;
                zone.MINY = pointToXY(eX, 0).y;
                selMode = false;
                zoom = true;
            }

        }
        repaint();
	}

}
