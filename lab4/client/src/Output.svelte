<script>
    export let response;
</script>

<main>
    {#each response as vehicle (vehicle.id)}
        <div>Id - {vehicle.id}</div>
        <div>Name - {vehicle.name}</div>
        <div>Creation Date - {vehicle.creationDate}</div>
        <div>Coordinate X - {vehicle.coordinates.x}</div>
        <div>Coordinate Y - {vehicle.coordinates.y}</div>
        <div>Engine Power - {vehicle.enginePower}</div>
        <div>Number of Wheels - {vehicle.numberOfWheels}</div>
        <div>Distance Travelled - {vehicle.distanceTravelled}</div>
        <div>Fuel Type - {vehicle.fuelType}</div>
        <br/>
    {/each}
</main>

<style>

</style>
