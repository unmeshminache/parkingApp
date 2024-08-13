
# Parking Application

This application is used to track records of parked vehicles on streets of Netherlands and  based on your parking time it will generate parking charges for your vehicle. It is also geneating the list of vehices not registerd at the time of parking and which will sent the notification letter for penalty to your home address.

below are the important urls

DB connection link : jdbc:mysql://localhost:3306/infyassignment
 
To register your car into parking 
Post : http://localhost:8091/register/

{
"vehicleNumber" : "MH01",
"entryDate":"2024-08-08",
"entryTime":"12:00:00",
"exitDate":"2024-08-13",
"exitTime":"16:00:00",
"streetName":"Azure"
}

To stop the registartion of vehicle
Put : http://localhost:8091/deregister/

{
"vehicleNumber" : "MH01",
"exitDate":"2024-08-13",
"exitTime":"16:00:00",
"streetName":"Azure"
}

to enter details which are monitored at streets by
Post http://localhost:8091/monitor/registervehicle/

[
         {
            "vehicleNumber": "MH01",
            "streetname": "Azure",
            "dateOfObservation": "2024-08-10",
            "timeOfObservation":"10:00:00"
         }
]




