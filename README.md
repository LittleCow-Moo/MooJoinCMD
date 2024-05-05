# MooJoinCMD
A simple plugin that runs command when player joins.
## Usage
1. Install the plugin.
2. Restart server to generate config.
3. Edit the command you want to run in `plugins/MooJoinCMD/config.yml`.<br>
Example config.yml:<br>
```yml
joinCommand: "say Welcome %player%"
```
Use `%player%` and it will be replaced with the player's name.
4. Restart the server or run `/rmoojoincmd` to apply the changes.