package org.hackillinois.app2017.Home

import org.hackillinois.app2017.Events.Event
import org.hackillinois.app2017.Utils
import spock.lang.Specification
import spock.lang.Unroll

import java.text.SimpleDateFormat

/**
 * Created by kevin on 11/21/2017.
 */
class HomeEventListSpec extends Specification {

    def "Test make HomeEventList"() {
        setup:
        HomeEventList homeEventList = new HomeEventList(new HomeTime(Calendar.getInstance(), "title"))

        expect:
        homeEventList.size() == 1
        homeEventList.syncEvents() // no events so it keeps the header
        homeEventList.size() == 1
    }

    @Unroll
    def "Test display=#shouldDisplay (#start, #end) at #currentTime"() {
        setup:
        Date currentDate = Utils.API_DATE_FORMAT.parse(currentTime)
        Date startDate = Utils.API_DATE_FORMAT.parse(start)
        Date endDate = Utils.API_DATE_FORMAT.parse(end)
        Event event = new Event("testEvent", startDate, endDate, [])

        expect:
        shouldDisplay == HomeEventList.shouldDisplayEvent(currentDate, event)

        where:
        currentTime                | start                      | end                        | shouldDisplay
        "2017-01-01T12:00:00.000Z" | "2017-01-01T00:00:00.000Z" | "2017-01-01T12:00:00.000Z" | false
        "2017-01-01T12:00:00.000Z" | "2017-01-01T06:00:00.000Z" | "2017-01-01T11:59:00.000Z" | false
        "2017-01-01T12:00:00.000Z" | "2017-01-01T06:00:00.000Z" | "2017-01-01T12:00:00.000Z" | true
        "2017-01-01T12:00:00.000Z" | "2017-01-01T08:00:00.000Z" | "2017-01-01T15:00:00.000Z" | true
    }
}
