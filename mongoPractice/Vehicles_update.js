<< 1 >>

db.getCollection("Vehicles").updateMany({}, {$set: {"fuel": 60}})

<< 2 >>

db.getCollection("Vehicles").updateMany({
    "status": "on Mission"
}, 
{
    $set: {
        "fuel": 35
        }
})

<< 3 >>

db.getCollection("Vehicles").updateMany(
  {},
  [
    { $set: { "fuel": { $add: ["$fuel", 20] } } },
    { $set: { "fuel": { $min: ["$fuel", 60] } } }
  ]
)

<< 4 >>

db.getCollection("Vehicles").updateMany({}, [
    { $set: { "fuelPercentage": "$fuel" } },
    { $set: { "fuelPercentage": { $multiply: ["$fuelPercentage", 1.666] } } },
    { $unset: "fuel" }
])

<< 5 >>

db.getCollection("Vehicles").updateMany({
    status: "in Repair"    
}, 
{
    $push: { "treatments": 
        {$each: [        
            {
                "treatmentDate": new Date(),
                "type": "brakes replacement",
                "cost": 1000,
            },
            {
                "treatmentDate": new Date(),
                "type": "wheels replacement",
                "cost": 1000,
            }
        ]}
    }
})

<< 6 >> // !

db.getCollection("Vehicles").updateMany(
  {
    treatments: { $exists: true, $ne: [] }
  },
  [
    {
      $set: {
        treatments: {
          $let: {
            vars: {
              oldestTreatment: { $min: "$treatments.treatmentDate" }
            },
            in: {
              $filter: {
                input: "$treatments",
                as: "treatment",
                cond: { $ne: ["$$treatment.treatmentDate", "$$oldestTreatment"] }
              }
            }
          }
        }
      }
    }
  ]
)

<< 7 >>

db.getCollection("Vehicles").updateMany(
  {
    treatments: { $exists: true }
  },
  {
      $push: { "treatments": { $each: [], $sort: {"cost": -1 } } }
  }
)


<< 8 >>

db.getCollection("Vehicles").insertOne(
  {
      "_id": "999-99-999",
      "type": "Lambourgini",
      "productionDate": new Date(),
      "status": "in Repair",
      "location": {
          "type": "Point",
          "coordinates": [
              -23.333212,
              71.116521
          ],
      },
      "distance": 1234,
      "treatments": [
          {
            "treatmentDate": new Date("2024-01-01"),
            "type": "oil inspection",
            "cost": 12500,  
          },
      ],
      "fuelPercentage": 100
  }
)