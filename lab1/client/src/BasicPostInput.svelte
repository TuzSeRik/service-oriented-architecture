<script>
    import Output from "./Output.svelte";

    export let response = [];
    let data = {
        'name': 'Example',
        'coordinate-x': 0,
        'coordinate-y': 0,
        'engine-power': 0,
        'number-of-wheels': 0,
        'distance-travelled': 0,
        'fuel-type': 'ALCOHOL'
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:8080/lab1_server_war_exploded/vehicles?"
            + new URLSearchParams(data).toString(),
            {
                method: 'POST'
            });
        response = [...response, await answer.json()];

        console.log(response);
    }
</script>

<main>
    <label>Name<input type="text" name="name" bind:value={data.name}></label>
    <label>Coordinate X<input type="number" name="coordinate-x" min="-885" bind:value={data["coordinate-x"]}></label>
    <label>Coordinate Y<input type="number" name="coordinate-y" min="29" bind:value={data["coordinate-y"]}></label>
    <label>Engine Power<input type="number" name="engine-power" min="0" bind:value={data["engine-power"]}></label>
    <label>Number of Wheels<input type="number" name="number-of-wheels" min="0" bind:value={data["number-of-wheels"]}></label>
    <label>Distance Travelled<input type="number" name="distance-travelled" min="0" bind:value={data["distance-travelled"]}></label>
    <label>
        Fuel Type
        <select name="fuel-type" bind:value={data["fuel-type"]}>
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
