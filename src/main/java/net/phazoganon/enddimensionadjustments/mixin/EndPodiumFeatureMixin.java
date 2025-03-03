package net.phazoganon.enddimensionadjustments.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;

@Mixin(EndPodiumFeature.class)
public class EndPodiumFeatureMixin extends Feature<NoneFeatureConfiguration> {
    @Mutable @Shadow @Final private static BlockPos END_PODIUM_LOCATION;
    @Shadow @Final private boolean active;
    public EndPodiumFeatureMixin(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        BlockPos blockpos = featurePlaceContext.origin();
        WorldGenLevel worldgenlevel = featurePlaceContext.level();
        int getX = blockpos.getX();
        int getY = blockpos.getY();
        int getZ = blockpos.getZ();
        Iterator var4 = BlockPos.betweenClosed(new BlockPos(getX-4, getY-1, getZ-4), new BlockPos(getX+4, getY+32, getZ+4)).iterator();
        while (true) {
            BlockPos blockpos1;
            boolean flag;
            do {
                if (!var4.hasNext()) {
                    if (getY <= -63.0) {
                        for (int i = 0; i < 4; ++i) {
                            this.setBlock(worldgenlevel, blockpos.above(128+i), Blocks.BEDROCK.defaultBlockState());
                        }
                    }
                    else {
                        for (int i = 0; i < 4; ++i) {
                            this.setBlock(worldgenlevel, blockpos.above(i), Blocks.BEDROCK.defaultBlockState());
                        }
                    }
                    BlockPos blockpos2 = blockpos.above(2);
                    Iterator var9 = Direction.Plane.HORIZONTAL.iterator();
                    while (var9.hasNext()) {
                        Direction direction = (Direction) var9.next();
                        if (getY <= -63.0) {
                            this.setBlock(worldgenlevel, new BlockPos(getX+direction.getStepX(), 66+direction.getStepY(), getZ+direction.getStepZ()), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, direction));
                        }
                        else {
                            this.setBlock(worldgenlevel, blockpos2.relative(direction), Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, direction));
                        }
                    }
                    return true;
                }
                blockpos1 = (BlockPos) var4.next();
                flag = blockpos1.closerThan(blockpos, 2.5);
            }
            while(!flag && !blockpos1.closerThan(blockpos, 3.5));
            int getX1 = blockpos1.getX();
            int getY1 = blockpos1.getY();
            int getZ1 = blockpos1.getZ();
            if (getX1 < getY) {
                if (flag) {
                    if (getY1 <= -63.0) {
                        this.setBlock(worldgenlevel, new BlockPos(getX1, getY1+128, getZ1), Blocks.BEDROCK.defaultBlockState());
                    }
                    else {
                        this.setBlock(worldgenlevel, blockpos1, Blocks.BEDROCK.defaultBlockState());
                    }
                }
                else if (getY1 < getY) {
                    if (blockpos1.getY() <= -63.0) {
                        this.setBlock(worldgenlevel, new BlockPos(getX1, getY1+128, getZ1), Blocks.BEDROCK.defaultBlockState());
                    }
                    else {
                        this.setBlock(worldgenlevel, blockpos1, Blocks.END_STONE.defaultBlockState());
                    }
                }
            }
            else if (getY1 > getY) {
                if (getY1 <= -63.0 || getY <= -63.0) {
                    this.setBlock(worldgenlevel, new BlockPos(getX1, getY1+128, getZ1), Blocks.AIR.defaultBlockState());
                }
                else {
                    this.setBlock(worldgenlevel, blockpos1, Blocks.AIR.defaultBlockState());
                }
            }
            else if (!flag) {
                if (getY1 <= -63.0) {
                    this.setBlock(worldgenlevel, new BlockPos(getX1, getY1+128, getZ1), Blocks.BEDROCK.defaultBlockState());
                }
                else {
                    this.setBlock(worldgenlevel, blockpos1, Blocks.BEDROCK.defaultBlockState());
                }
            }
            else if (this.active) {
                if (getY1 <= -63.0) {
                    this.setBlock(worldgenlevel, new BlockPos(getX1, getY1+128, getZ1), Blocks.END_PORTAL.defaultBlockState());
                }
                else {
                    this.setBlock(worldgenlevel, new BlockPos(blockpos1), Blocks.END_PORTAL.defaultBlockState());
                }
            }
            else {
                if (getY1 <= -63.0) {
                    this.setBlock(worldgenlevel, new BlockPos(getX1, getY1+128, getZ1), Blocks.AIR.defaultBlockState());
                }
                else {
                    this.setBlock(worldgenlevel, new BlockPos(blockpos1), Blocks.AIR.defaultBlockState());
                }
            }
        }
    }
    static {
        END_PODIUM_LOCATION = new BlockPos(0, 64, 0);
    }
}