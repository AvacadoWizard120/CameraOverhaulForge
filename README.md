![Img](https://i.imgur.com/H3UrLFP.png)

# 📷 Camera Overhaul v2.0!
A full rewrite of the mod, featuring (Neo)Forge support, an explosion screen shaking system, an idle camera sway system, vehicles & mounts support, even more granular configuration, a reimplementation of mouselook tilting, and major improvements to all other features implemented prior. Phew!
## ⭐ New Features
- Official support for **NeoForge**, from `1.20.6` to `1.21.2` and newer, as well as legacy **Forge**, from `1.17` to `1.20.5`.
  Pull Requests adding support for even older versions are welcome, everything compiles from one codebase using Stonecutter.
- New `Screen Shakes` feature.
  Explosions, lightning, thunder, and many other events will now shake players' cameras, using a top-notch implementation featuring simplex noise and exponential decay.
- New `Camera Sway` feature.
  Noise-based camera offsets that kick in when you don't move your camera and legs for a short moment.
  Configurable with `Intensity`, `Frequency`, `Fade-In Delay`, `Fade-In Length`, and `Fade-Out Length`.
- Completely reworked the `Yaw Delta Roll` feature, now known as `Mouselook Roll`.
  Configurable in 3 components - `Intensity`, `Accumulation`, and `Decay Smoothness`.
- The mod's features now apply when using vehicles and mounts.
- Configuration expanded and reworked, with separate values for walking, swimming, elytra flight, as well as vehicles and mounts!
- The manual configuration file now uses TOML, which is much more human-writable, as every present field there is now properly commented.
- Added a separate toggle for the mod's effects while in third person.
- [Full Changelog](<https://github.com/Mirsario/Minecraft-CameraOverhaul/blob/dev/CHANGELOG.md>)


## ❗ What's this?!
**CameraOverhaul** is a highly configurable clientside Minecraft mod that attempts to improve overall satisfaction of the game through the introduction of various camera rotations, emphasizing the player's movement and improving visual feedback.
Strafing and turning around will tilt the camera sideways. Jumping, falling, and moving forward or backwards will affect the camera's pitch. Nearby explosions will shake the screen, and ceasing movement for enough time will cause the camera to gently sway around while waiting for your input.

### Info:
- **Available** at [Modrinth](https://modrinth.com/mod/cameraoverhaul) & [CurseForge](https://www.curseforge.com/minecraft/mc-mods/cameraoverhaul)!
- **Supports** [Fabric](https://fabricmc.net), [Quilt](https://quiltmc.org), [NeoForge](https://neoforged.net), as well as legacy Forge!
- **Supports** Minecraft versions from `1.14` to `1.21` and newer! Make sure you get the right release;
- **Requires** [Cloth Config API](https://modrinth.com/mod/cloth-config)!
- Feels best at high framerates! [Sodium](https://modrinth.com/mod/sodium), [Lithium](https://modrinth.com/mod/lithium), and other [optimization mods](https://modrinth.com/mods?f=categories:optimization) are **recommended**!

## ⚙️ Can I turn off X and only keep Y?
You can use in-game configuration screens ([Mod Menu](https://modrinth.com/mod/modmenu) if on Fabric/Quilt) to configure the mod in-game.

You can also manually edit the well-documented configuration file with intensity scaling for all features at<br/>
`.minecraft/config/cameraoverhaul.toml`.<br/>
Set any factor to `0.0` to turn its feature off.<br/>
Negative numbers are also usually legal, allowing inverting effects, if that's your thing.

## 📖 License
Released under the [GNU General Public License 3.0](https://github.com/Mirsario/Minecraft-CameraOverhaul/blob/dev/LICENSE.md).
<br/>
Copyright (c) 2020-2025 Mirsario & Contributors.

## ❤️ Contributing
This project uses the [Stonecutter](https://stonecutter.kikugie.dev) comment-macro preprocessor for multi-version support.
- The IntelliJ [Stonecutter Dev](https://plugins.jetbrains.com/plugin/25044-stonecutter-dev) plugin is highly recommended. Manually install it in zip form if you see complaints about your IDE version not being supported.
- After getting the repository's source code, use `gradlew tasks` to list available Gradle tasks.
- Use the `buildAll` Gradle task to properly build all targets of the mod.
- Use tasks under the `Stonecutter` group to "checkout" specific versions, running the preprocessor on the source code and updating all comment blocks.

Thank you to the following contributors for helping improve the mod for everyone!

<a href="https://github.com/Mirsario/Minecraft-CameraOverhaul/graphs/contributors">
	<img src="https://contrib.rocks/image?repo=Mirsario/Minecraft-CameraOverhaul&max=900&columns=20" />
</a>
