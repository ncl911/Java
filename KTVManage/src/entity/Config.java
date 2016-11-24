package entity;

import java.awt.Font;
import java.io.File;

import javax.swing.ImageIcon;

public class Config {
	private String name;
	private Font font;
	private String copyright;
	private String background;
	private ImageIcon backgroundImage;

	public Config(String name) {
		this(name,  "",new Font("微软雅黑",
				Font.PLAIN, 14));
	}

	public Config(String name, String background) {
		this(name,  background, new Font(
				"微软雅黑", Font.PLAIN, 14));
	}

	public Config(String name,String background, Font font) {
		this.name = name;
		this.font = font;
		this.copyright = "";
		this.background = background;
		File file = new File(background);
		if (file.exists()) {
			backgroundImage = new ImageIcon(background);
		} else {
			background = "";
			backgroundImage = new ImageIcon(this.getClass().getResource(
					"/images/bg.jpg"));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		File file = new File(background);
		if (file.exists()) {
			backgroundImage = new ImageIcon(background);
		} else {
			background = "";
			backgroundImage = new ImageIcon(this.getClass().getResource(
					"/images/bg.jpg"));
		}
		this.background = background;
	}

	public ImageIcon getBackgroundImage() {
		return backgroundImage;
	}

	public String getCopyright() {
		return copyright;
	}
}
