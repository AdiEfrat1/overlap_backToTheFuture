<< 1 >>

db.getCollection("Vehicles").find({
    _id: "55-234-45"
})

<< 2 >>

db.getCollection("Vehicles").find({
    status: "on Mission"
}, 
{
    "_id": 1, 
    "location": 1
})

<< 3 >>

db.getCollection("Vehicles").find({
    status: { 
        $ne: "on Mission" 
    }
}, 
{
    "_id": 1, 
    "productionDate": 1
})


<< 4 >>

db.getCollection("Vehicles").find().sort({ "productionDate": 1 }).limit(3)

<< 5 >> // unneeded

db.getCollection("Vehicles").aggregate(
   [
     { $sort : { "productionDate" : -1 } },
     { $limit: 3 }
   ]
)

<< 6 >>

db.getCollection("Vehicles").find({
    "productionDate": {
        $gt: new Date("2010-01-01")
    }
})

<< 7 >>

db.getCollection("Vehicles").find({
     $expr: {
       $in: [{ $month: "$productionDate" }, [7, 8]]
     }
}, 
{
    "_id": 1,
    "productionYear": {$year: "$productionDate"}
})

<< 8 >>

db.getCollection("Vehicles").createIndex({ location: "2dsphere" })

db.getCollection("Vehicles").find({
  location: {
    $nearSphere: {
      $geometry: { type: "Point", coordinates: [13, 15] },
      $maxDistance: 50000
    }
  }
});

<< 9 >>

db.yourCollection.createIndex({ location: "2dsphere" });

db.getCollection("Vehicles").find({
  location: {
    $geoWithin: {
      $geometry: { 
          "type": "Polygon", 
          "coordinates": [
              [
                  [0, 0],
                  [-90, 0],
                  [-90, -90],
                  [0, -90],
                  [0, 0]
              ]
          ]
      }
    }
  }
});

<< 10 >>

db.getCollection("Vehicles").find({
    "treatments": {
        $exists: true,
    }
});

<< 11 >>

db.getCollection("Vehicles").find({
  "treatments": { $exists: true },
  $expr: {
      $gt: [{ $size: "$treatments" }, 3] 
  }
})

<< 12 >>

db.getCollection("Vehicles").find({
  "treatments": {$elemMatch: {"type": "oil inspection"}}
})