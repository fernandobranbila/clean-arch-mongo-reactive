package com.aegro.domain

import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Harvest
import com.aegro.domain.farm.model.Plot
import java.time.LocalDate
import java.time.LocalDateTime

class MockUtil {

    companion object {

        fun createFarmMock(
            id: String = "1",
            description: String = "test",
            plots: List<Plot>? = null
        ) =
            Farm(
                id = id,
                description = description,
                plots = plots
            )

        fun createPlotsMock(
            plotQuantity: Int = 1,
            harvestQuantity: Int = 0,
        ): List<Plot> {
            val plots = mutableListOf<Plot>()
            for (i in 1..plotQuantity) {
                plots.add(
                    Plot(
                        id = i.toString(),
                        description = "plot $i",
                        area = (1..10).random().toFloat(),
                        harvests = createHarvestsMock(harvestQuantity)
                    )
                )
            }
            return plots
        }

        fun createHarvestsMock(
            quantity: Int = 1
        ): List<Harvest> {
            val harvests = mutableListOf<Harvest>()
            for (i in 1..quantity) {
                harvests.add(
                    Harvest(
                        id = i.toString(),
                        description = "plot $i",
                        productivity = (1..10).random(),
                        dateAndTime = LocalDateTime.of(
                            (1990..2020).random(),
                            (1..12).random(),
                            (1..28).random(),
                            (1..23).random(),
                            (1..59).random()
                        )
                    )
                )
            }
            return harvests
        }

        fun generateRandomLocalDateTime() =
            LocalDateTime.of(
                (1990..2020).random(),
                (1..12).random(),
                (1..28).random(),
                (1..23).random(),
                (1..59).random()
            )

        fun generateRandomLocalDate() =
            LocalDate.of(
                (1990..2020).random(),
                (1..12).random(),
                (1..28).random(),
            )

    }


}