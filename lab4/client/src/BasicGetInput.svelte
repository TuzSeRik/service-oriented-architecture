<script>
    import Output from "./Output.svelte";

    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'id': 0,
        'idSign': 'EQ',
        'name': 'Example',
        'nameSign': 'LK',
        'coordinateX': 0,
        'xSign': 'EQ',
        'coordinateY': 0,
        'ySign': 'EQ',
        'enginePower': 0,
        'engineSign': 'EQ',
        'numberOfWheels': 0,
        'wheelsSign': 'EQ',
        'distanceTravelled': 0,
        'distanceSign': 'EQ',
        'fuelType': 'ALCOHOL',
        'sortField': 'id',
        'isDesc': true,
        'limit': 100,
        'page': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:7443/vehicles?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET',
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <label>Id<input type="number" name="id" bind:value={data.id} /></label>
    <label>
        Id Sign
        <select name="id-sign" bind:value={data["idSign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Name<input type="text" name="name" bind:value={data.name} /></label>
    <label>
        Name Sign
        <select name="name-sign" bind:value={data["nameSign"]}>
            <option value="LK">Like</option>
            <option value="EQ">Equal</option>
        </select>
    </label>

    <label>Coordinate X<input type="number" name="coordinate-x" min="-885" bind:value={data["coordinateX"]} /></label>
    <label>
        X Sign
        <select name="x-sign" bind:value={data["xSign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Coordinate Y<input type="number" name="coordinate-y" min="-29" bind:value={data["coordinateY"]} /></label>
    <label>
        Y Sign
        <select name="y-sign" bind:value={data["ySign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Engine Power<input type="number" name="engine-power" min="0" bind:value={data["enginePower"]} /></label>
    <label>
        Engine Sign
        <select name="engine-sign" bind:value={data["engineSign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Number of Wheels<input type="number" name="number-of-wheels" min="0" bind:value={data["numberOfWheels"]} /></label>
    <label>
        Wheels Sign
        <select name="wheels-sign" bind:value={data["wheelsSign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Distance Travelled<input type="number" name="distance-travelled" min="0"
                                    bind:value={data["distanceTravelled"]} /></label>
    <label>
        Distance Sign
        <select name="distance-sign" bind:value={data["distanceSign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>
        Fuel Type
        <select name="fuel-type" bind:value={data["fuelType"]}>
            <option value="ALCOHOL">Alcohol</option>
            <option value="MANPOWER">Manpower</option>
            <option value="NUCLEAR">Nuclear</option>
        </select>
    </label>

    <label>
        Sort Field
        <select name="sort-field" bind:value={data["sortField"]}>
            <option value="id">Id</option>
            <option value="name">Name</option>
            <option value="coordinateX">Coordinate X</option>
            <option value="coordinateY">Coordinate Y</option>
            <option value="enginePower">Engine Power</option>
            <option value="numberOfWheels">Number of Wheels</option>
            <option value="distanceTravelled">Distance Travelled</option>
            <option value="fuelType">Fuel Type</option>
        </select>
    </label>
    <label>
        Sort Sign
        <select name="isDesc" bind:value={data["isDesc"]}>
            <option value="true">Less</option>
            <option value="false">Greater</option>
        </select>
    </label>

    <label>Limit<input type="number" name="limit" min="0" bind:value={data.limit} /></label>
    <label>Page<input type="number" name="page" min="1" bind:value={data.page} /></label>

    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
