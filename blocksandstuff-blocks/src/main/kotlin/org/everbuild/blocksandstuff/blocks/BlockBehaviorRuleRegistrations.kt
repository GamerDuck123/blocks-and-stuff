package org.everbuild.blocksandstuff.blocks

import net.minestom.server.MinecraftServer
import org.everbuild.blocksandstuff.blocks.group.VanillaBlockBehaviour
import org.everbuild.blocksandstuff.blocks.group.behaviour.BehaviourGroup

object BlockBehaviorRuleRegistrations {
    private val logger = MinecraftServer.LOGGER

    @JvmStatic
    fun registerDefault() {
        register(*VanillaBlockBehaviour.ALL.toTypedArray<BehaviourGroup>())
    }

    @JvmStatic
    fun register(vararg blockGroups: BehaviourGroup) {
        val blockManager = MinecraftServer.getBlockManager()
        var count = 0

        for (group in blockGroups) {
            val blockGroup = group.blockGroup
            for (block in blockGroup.allMatching()) {
                count++
                val handler = group.createHandler(block)
                blockManager.registerHandler(block.key().asString()) { handler }
            }
        }

        logger.info("Registered $count handlers")
    }
}