package cn.com.nttdatabj.h2.uq.window.menu.tools;

import javax.swing.JMenuItem;

import cn.com.nttdatabj.h2.uq.window.menu.MenuItems;

import com.sun.glass.events.KeyEvent;

public class MenuItemIPC extends MenuItems {

	public JMenuItem CreateMenuItem(){
		JMenuItem mntmOpen = new JMenuItem("IP電卓(I)", KeyEvent.VK_I);
		mntmOpen.addActionListener(new MenuItemIPCAction());
		return mntmOpen;
	}

}
