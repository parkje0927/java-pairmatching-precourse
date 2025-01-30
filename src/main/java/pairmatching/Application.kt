package pairmatching

import pairmatching.service.PairMatchingManagement

class Application

fun main(args: Array<String>) {

    val pairMatchingManagement = PairMatchingManagement()
    pairMatchingManagement.run()
}

