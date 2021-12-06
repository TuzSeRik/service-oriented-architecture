<script>
    import Output from "./Output.svelte";

    export let response = [];
    let data = {
        'id': 0,
        'id-sign': 'EQ',
        'name': 'Example',
        'name-sign': 'LK',
        'coordinate-x': 0,
        'x-sign': 'EQ',
        'coordinate-Y': 0,
        'y-sign': 'EQ',
        'engine-power': 0,
        'engine-sign': 'EQ',
        'number-of-wheels': 0,
        'wheels-sign': 'EQ',
        'distance-travelled': 0,
        'distance-sign': 'EQ',
        'fuel-type': 'ALCOHOL',
        'sort-field': 'id',
        'sort-sign': 'LS',
        'limit': 100,
        'page': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:8080/lab1_server_war_exploded/vehicles?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET'
            });
        response = [...response, ...await answer.json()];

        console.log(response);
    }
</script>

<main>
    <label>Id<input type="number" name="id" bind:value={data.id} /></label>
    <label>
        Id Sign
        <select name="id-sign" bind:value={data["id-sign"]}>
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
        <select name="name-sign" bind:value={data["name-sign"]}>
            <option value="LK">Like</option>
            <option value="EQ">Equal</option>
        </select>
    </label>

    <label>Coordinate X<input type="number" name="coordinate-x" min="-885" bind:value={data["coordinate-x"]} /></label>
    <label>
        X Sign
        <select name="x-sign" bind:value={data["x-sign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Coordinate Y<input type="number" name="coordinate-y" min="-29" bind:value={data["coordinate-Y"]} /></label>
    <label>
        Y Sign
        <select name="y-sign" bind:value={data["y-sign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Engine Power<input type="number" name="engine-power" min="0" bind:value={data["engine-power"]} /></label>
    <label>
        Engine Sign
        <select name="engine-sign" bind:value={data["engine-sign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Number of Wheels<input type="number" name="number-of-wheels" min="0" bind:value={data["number-of-wheels"]} /></label>
    <label>
        Wheels Sign
        <select name="wheels-sign" bind:value={data["wheels-sign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>Distance Travelled<input type="number" name="distance-travelled" min="0"
                                    bind:value={data["distance-travelled"]} /></label>
    <label>
        Distance Sign
        <select name="distance-sign" bind:value={data["distance-sign"]}>
            <option value="LS">Less</option>
            <option value="LE">Less or Equal</option>
            <option value="EQ">Equal</option>
            <option value="GE">Greater or Equal</option>
            <option value="GR">Greater</option>
        </select>
    </label>

    <label>
        Fuel Type
        <select name="fuel-type" bind:value={data["fuel-type"]}>
            <option value="ALCOHOL">Alcohol</option>
            <option value="MANPOWER">Manpower</option>
            <option value="NUCLEAR">Nuclear</option>
        </select>
    </label>

    <label>
        Sort Field
        <select name="sort-field" bind:value={data["sort-field"]}>
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
        <select name="sort-sign" bind:value={data["sort-sign"]}>
            <option value="LS">Less</option>
            <option value="GR">Greater</option>
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
