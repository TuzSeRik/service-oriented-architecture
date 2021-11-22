<script>
    import Output from "./Output.svelte";

    export let response = [];
    let data = {
        'wheels-number-equal': 0
    }

    const sendData = async () => {
        let answer = await fetch("http://localhost:8080/lab1_server_war_exploded/wheels-number-searcher?"
            + new URLSearchParams(data).toString(),
            {
                method: 'GET'
            });
        response = [...response, ...await answer.json()];

        console.log(response);
    }
</script>

<main>
    <label>Wheels Number Equal<input type="number" name="wheels-number-equal" min="0"
                                    bind:value={data["wheels-number-equal"]}></label>
    <button on:click={() => sendData()}>Send Data</button>

    <Output {response} />
</main>

<style>
    main {
        display: flex;
        flex-direction: column;
    }
</style>
