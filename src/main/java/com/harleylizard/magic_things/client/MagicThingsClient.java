package com.harleylizard.magic_things.client;

import com.harleylizard.magic_things.common.SetBiomesPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public final class MagicThingsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SetBiomesPayload.TYPE, (payload, context) -> {

        });

    }

}
