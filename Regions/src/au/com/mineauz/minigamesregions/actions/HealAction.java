package au.com.mineauz.minigamesregions.actions;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import au.com.mineauz.minigames.MinigamePlayer;
import au.com.mineauz.minigames.menu.Menu;
import au.com.mineauz.minigames.menu.MenuItemInteger;
import au.com.mineauz.minigames.properties.types.IntegerProperty;
import au.com.mineauz.minigamesregions.Node;
import au.com.mineauz.minigamesregions.Region;

public class HealAction extends ActionInterface{
	
	private final IntegerProperty heal = new IntegerProperty(1, "amount");
	
	public HealAction() {
		properties.addProperty(heal);
	}

	@Override
	public String getName() {
		return "HEAL";
	}

	@Override
	public String getCategory() {
		return "World Actions";
	}

	@Override
	public boolean useInRegions() {
		return true;
	}

	@Override
	public boolean useInNodes() {
		return true;
	}

	@Override
	public void executeNodeAction(MinigamePlayer player,
			Node node) {
		execute(player);
	}

	@Override
	public void executeRegionAction(MinigamePlayer player, Region region) {
		execute(player);
	}
	
	private void execute(MinigamePlayer player){
		if(player == null || !player.isInMinigame()) return;
		if(heal.getValue() > 0){
			if(player.getPlayer().getHealth() != 20){
				double health = heal.getValue() + player.getPlayer().getHealth();
				if(health > 20)
					health = 20;
				player.getPlayer().setHealth(health);
			}
		}
		else
			player.getPlayer().damage(heal.getValue() * -1);
	}

	@Override
	public void saveArguments(FileConfiguration config, String path) {
	}

	@Override
	public void loadArguments(FileConfiguration config, String path) {
	}

	@Override
	public boolean displayMenu(MinigamePlayer player, Menu previous) {
		Menu m = new Menu(3, "Heal");
		m.addItem(new MenuItemInteger("Heal Amount", Material.GOLDEN_APPLE, heal, Integer.MIN_VALUE, Integer.MAX_VALUE));
		m.displayMenu(player);
		return true;
	}

}
