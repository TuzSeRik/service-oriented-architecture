<script>
    import Output from "./Output.svelte";

    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'id': 0,
        'name': 'Example',
        'coordinates': {
            'id': 0,
            'x': 0,
            'y': 0
        },
        'enginePower': 0,
        'numberOfWheels': 0,
        'distanceTravelled': 0,
        'fuelType': 'ALCOHOL'
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:7443/vehicles",
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <label>Id<input type="number" name="id" bind:value={data.id} /></label>
    <label>Name<input type="text" name="name" bind:value={data.name}></label>
    <label>Coordinate X<input type="number" name="coordinate-x" min="-885" bind:value={data["coordinates"]["x"]}></label>
    <label>Coordinate Y<input type="number" name="coordinate-y" min="-29" bind:value={data["coordinates"]["y"]}></label>
    <label>Engine Power<input type="number" name="engine-power" min="0" bind:value={data["enginePower"]}></label>
    <label>Number of Wheels<input type="number" name="number-of-wheels" min="0"
                                  bind:value={data["numberOfWheels"]}></label>
    <label>Distance Travelled<input type="number" name="distance-travelled" min="0" bind:value={data["distanceTravelled"]}></label>
    <label>
        Fuel Type
        <select name="fuel-type" bind:value={data["fuelType"]}>
            <option value="ALCOHOL">Alcohol</option>
            <option value="MANPOWER">Manpower</option>
            <option value="NUCLEAR">Nuclear</option>
        </select>
    </label>
    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
