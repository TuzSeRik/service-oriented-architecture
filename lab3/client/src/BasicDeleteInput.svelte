<script>
    let responseBody = {
        vehicles: []
    }
    $: response = responseBody["vehicles"];
    let data = {
        'id': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:888/vehicles?"
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
