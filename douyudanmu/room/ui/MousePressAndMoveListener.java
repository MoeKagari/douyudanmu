package douyudanmu.room.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * 处理鼠标拖动事件
 * @author MoeKagari
 *
 */
public class MousePressAndMoveListener implements MouseMotionListener, MouseListener {
	private UIFrame ui;

	public MousePressAndMoveListener(UIFrame ui) {
		this.ui = ui;
	}

	private Point oldLocation;
	private Point newLocation;

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		oldLocation = e.getPoint();
	}

	public void mouseReleased(MouseEvent e) {
		oldLocation = null;
		newLocation = null;
	}

	public void mouseDragged(MouseEvent e) {
		newLocation = e.getPoint();
		ui.setLocation(
				(int) (ui.getLocation().getX() + newLocation.getX() - oldLocation.getX()),
				(int) (ui.getLocation().getY() + newLocation.getY() - oldLocation.getY())
				);
	}

	public void mouseMoved(MouseEvent e) {
	}
}
