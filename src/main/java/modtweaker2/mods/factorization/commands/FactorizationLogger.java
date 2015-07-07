package modtweaker2.mods.factorization.commands;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import modtweaker2.helpers.InputHelper;
import modtweaker2.helpers.ReflectionHelper;
import modtweaker2.helpers.StringHelper;
import modtweaker2.mods.factorization.FactorizationHelper;
import net.minecraft.item.ItemStack;

public class FactorizationLogger implements ICommandFunction {

    private static final List<String> validArguments = new LinkedList<String>();
    
    static {
        validArguments.add("crystallizer");
        validArguments.add("lacerator");
        validArguments.add("slagfurnace");
    }
    
    @Override
    public void execute(String[] arguments, IPlayer player) {
        List<String> args = StringHelper.toLowerCase(Arrays.asList(arguments));
        
        if(!validArguments.containsAll(args)) {
            if(player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("Invalid arguments for command. Valid arguments: " + StringHelper.join(validArguments, ", ")));
            }
        } else {
            if(args.isEmpty() || args.contains("crystallizer")) {
                for(Object recipe : FactorizationHelper.crystallizer) {
                    MineTweakerAPI.logCommand(String.format("mods.factorization.Crystallizer.addRecipe(%s, %s, %s, %s);",
                            InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "input")),
                            InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "output")),
                            InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "solution")),
                            ReflectionHelper.<Float>getObject(recipe, "output_count")));
                }
            }
            
            if(args.isEmpty() || args.contains("lacerator")) {
                for(Object recipe : FactorizationHelper.lacerator) {
                    MineTweakerAPI.logCommand(String.format("mods.factorization.Lacerator.addRecipe(%s, %s, %s);",
                            ReflectionHelper.<String>getObject(recipe, "oreName") != null ? 
                                InputHelper.getStackDescription(ReflectionHelper.<String>getObject(recipe, "oreName")) :
                                InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "itemstack")),
                            InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "output")),
                            ReflectionHelper.<Float>getObject(recipe, "probability")));
                }
            }
            
            if(args.isEmpty() || args.contains("slagfurnace")) {
                for(Object recipe : FactorizationHelper.slag) {
                    MineTweakerAPI.logCommand(String.format("mods.factorization.SlagFurnace.addRecipe(%s, %s, %s, %s, %s);",
                            InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "input")),
                            InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "output2")),
                            ReflectionHelper.<Float>getObject(recipe, "prob2"),
                            InputHelper.getStackDescription(ReflectionHelper.<ItemStack>getObject(recipe, "output1")),
                            ReflectionHelper.<Float>getObject(recipe, "prob1")));
                }
            }
            
            if (player != null) {
                player.sendChat(MineTweakerImplementationAPI.platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
            }
        }
    }
}