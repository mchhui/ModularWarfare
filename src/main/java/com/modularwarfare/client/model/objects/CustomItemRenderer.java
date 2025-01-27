package com.modularwarfare.client.model.objects;

import com.modularwarfare.ModularWarfare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomItemRenderer {
    public static ResourceLocation NULL_TEX=new ResourceLocation(ModularWarfare.MOD_ID,"textures/nulltex.png");
    protected static TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
    private HashMap<String, ResourceLocation> cachedSkins = new HashMap<String, ResourceLocation>();
    private ArrayList<String> cachedBadSkins =new ArrayList<String>();

    public void renderItem(CustomItemRenderType type, EnumHand hand, ItemStack item, Object... data) {
    }

    public void bindTexture(String type, String fileName) {
        bindTexture(type, fileName, false,true);
    }
    
    public boolean bindTextureGlow(String type, String fileName) {
        if(cachedBadSkins.contains(type + "_" + fileName+"_glow")) {
            return false;
        }
        bindTexture(type, fileName+"_glow", true,false);
        return true;
    }
    
    public void bindTexture(String type, String fileName,boolean saveBad,boolean printException) {
        String pathFormat = "skins/%s/%s.png";

        if (renderEngine == null)
            renderEngine = Minecraft.getMinecraft().renderEngine;
        if(cachedBadSkins.contains(type + "_" + fileName)) {
            renderEngine.bindTexture(NULL_TEX);
            return;
        }

        try {
            ResourceLocation resourceLocation = new ResourceLocation(ModularWarfare.MOD_ID, String.format(pathFormat, type, fileName));
            if (cachedSkins.containsKey(type + "_" + fileName)) {
                renderEngine.bindTexture(cachedSkins.get(type + "_" + fileName));
                return;
            } else if (renderEngine.getTexture(resourceLocation) == null) {
                ITextureObject itextureobject = new SimpleTexture(resourceLocation);
                itextureobject.loadTexture(Minecraft.getMinecraft().getResourceManager());
            }

            renderEngine.bindTexture(resourceLocation);
        } catch (Exception e) {
            ResourceLocation resourceLocation = new ResourceLocation(ModularWarfare.MOD_ID,
                    String.format(pathFormat, "default", type, fileName));
            cachedSkins.put(type + "_" + fileName, resourceLocation);
            if(saveBad) {
                cachedBadSkins.add(type + "_" + fileName);
            }
            if(printException) {
                e.printStackTrace();
            }
        }
    }
}
