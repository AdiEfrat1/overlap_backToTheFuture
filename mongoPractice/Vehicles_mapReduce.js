<< difference between aggregation and the map-reduce operation >>

/*
map-reduce tends to be slower than aggregations, and also less flexible and expressive.
map-reduce is used when needing custom proccessing you can't achive easily with aggregations, 
but aggregations should be the first choice generally due to performence & ease of use.

*/
<< map >>

let mapFunction = function() {
  emit(this.type, this.treatments);
};

let reduceFunction = function(key, values) {
  let result = { sum: 0, count: 0 }
  
  values.forEach((arr) => {
      if (arr) {
          arr.forEach((treatment) => {
              result.sum += treatment.cost;
              result.count += 1;
          })   
      }
  });
  
  result.avg = result.sum / result.count;
  
  return result;
};

let finalizeFunction = function(key, reducedValue) {
  return { count: reducedValue.count, avgCost: reducedValue.avg }
}

db.getCollection("Vehicles").mapReduce(
    mapFunction,
    reduceFunction,
    {
        out: { inline: 1 },
        finalize: finalizeFunction,
    }
)