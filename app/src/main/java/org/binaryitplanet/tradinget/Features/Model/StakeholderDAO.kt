package org.binaryitplanet.tradinget.Features.Model

import androidx.room.*
import org.binaryitplanet.tradinget.Utils.StakeholderUtils

@Dao
interface StakeholderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stakeholderUtils: StakeholderUtils): Long

    @Delete
    fun delete(stakeholderUtils: StakeholderUtils): Int

    @Update
    fun update(stakeholderUtils: StakeholderUtils): Int

    @Query("SELECT * FROM Stakeholder_table WHERE Type ==:type ORDER BY Name ASC")
    fun getStakeholderByType(type: Int): List<StakeholderUtils>

    @Query("SELECT * FROM Stakeholder_table WHERE ID ==:id")
    fun getStakeholderById(id: Long): StakeholderUtils
}