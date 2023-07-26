<< 1 >> 

db.getCollection("Vehicles").aggregate([
  { $addFields: { treatments: { $ifNull: [ "$treatments", {} ] } } },
  { $unwind: "$treatments" },
  { $group: { _id: { status: "$status", vehicleId: "$_id" }, vehicle: { $first: "$$ROOT" }, allTreatmentsCost: { $sum: "$treatments.cost" } } },
  { $group: { _id: "$_id.status", vehicles: { $addToSet: "$vehicle" }, totalTreatmentsCost: { $sum: "$allTreatmentsCost" } } },
  { $match: { totalTreatmentsCost: { $gte: 10000 } } }
])

<< 2 >>

db.getCollection("Vehicles").aggregate([
    { $set: { "treatments": { $ifNull: ["$treatments", [] ] } } },
    { $group: { _id: { $year: "$productionDate" }, avgTreatmentCount: { $avg: { $size: "$treatments" } } } },
    { $project: {"_id": 0, "year": "$_id", "avgTreatmentCount": 1} }
])

<< 3 >>

db.getCollection("Vehicles").aggregate([
    { $unwind: "$treatments" },
    { $match: { $expr: { $eq: ["$treatments.type", "oil inspection"] } } },
    { $project: {_id: 0, treatment: "$treatments"} }
])

<< 4 >>

db.getCollection("Vehicles").aggregate([
    { $sort: { "distance": -1 } },
    { $group: { _id: "$type", mostKM: { $first: "$$ROOT" }, leastKM: { $last: "$$ROOT" } } },
])