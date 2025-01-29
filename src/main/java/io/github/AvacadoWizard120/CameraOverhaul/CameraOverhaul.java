// Copyright 2020-2025 Mirsario & Contributors.
// Released under the GNU General Public License 3.0.
// See LICENSE.md for details.

package io.github.AvacadoWizard120.CameraOverhaul;

import io.github.AvacadoWizard120.CameraOverhaul.configuration.Configuration;
import io.github.AvacadoWizard120.CameraOverhaul.systems.CameraSystem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
	modid = CameraOverhaul.MODID,
	name = CameraOverhaul.NAME,
	version = CameraOverhaul.VERSION
)
public class CameraOverhaul {
	public static final String MODID = "cameraoverhaul";
	public static final String NAME = "Camera Overhaul";
	public static final String VERSION = "2.0";

	public CameraOverhaul()
	{
		onInitializeClient();
	}
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public static CameraSystem camera;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration.init(event);
	}

	public static void onInitializeClient()
	{
		camera = new CameraSystem();
	}
}
