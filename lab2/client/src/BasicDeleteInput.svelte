<script>
    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'id': 0
    }

    const sendData = async () => {
        let answer = await fetch("https://localhost:8181/service-one/vehicles?"
        // let answer = await fetch("https://localhost:8443/service-one-0.9.0/vehicles?"
            + new URLSearchParams(data).toString(),
            {
                method: 'DELETE'
            });
        responseBody = await answer.json();
    }
</script>

<main>
    <label>Id<input type="number" name="id" bind:value={data.id}/></label>
    <button on:click={() => sendData()}>Send Data</button>
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
