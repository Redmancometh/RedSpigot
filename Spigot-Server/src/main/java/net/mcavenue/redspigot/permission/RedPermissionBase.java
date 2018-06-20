package net.mcavenue.redspigot.permission;

import java.util.Set;

import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.ServerOperator;

public class RedPermissionBase extends PermissibleBase {

	public RedPermissionBase(ServerOperator opable) {
		super(opable);
	}

	@Override
	public boolean isPermissionSet(Permission perm) {
		// TODO Auto-generated method stub
		return super.isPermissionSet(perm);
	}

	@Override
	public synchronized void clearPermissions() {
		// TODO Auto-generated method stub
		super.clearPermissions();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		// TODO Auto-generated method stub
		return super.getEffectivePermissions();
	}

	@Override
	public boolean hasPermission(String inName) {
		// TODO Auto-generated method stub
		return super.hasPermission(inName);
	}

	@Override
	public boolean hasPermission(Permission perm) {
		// TODO Auto-generated method stub
		return super.hasPermission(perm);
	}

	@Override
	public boolean isPermissionSet(String name) {
		// TODO Auto-generated method stub
		return super.isPermissionSet(name);
	}

	@Override
	public void recalculatePermissions() {
		// TODO Auto-generated method stub
		super.recalculatePermissions();
	}

}
