<script>
    export let response = [];
    let data = {
        'id': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:8080/lab1_server_war_exploded/basic-operations-performer?"
            + new URLSearchParams(data).toString(),
            {
                method: 'DELETE'
            });
        response = [...response, await answer.json()];

        console.log(response);
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
